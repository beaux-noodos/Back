package com.example.templet.template.sucgestIAWithReaction;

import static com.example.templet.service.utils.AIUtils.passesThrough0WithFiniteLimit;
import static com.example.templet.service.utils.AIUtils.takeSymbolicAiPoint;
import static com.example.templet.template.chat.AIConstant.BASE_LIKE_POINT_RATE;
import static com.example.templet.template.chat.AIConstant.BASE_STARS_POINT_RATE;
import static com.example.templet.template.chat.AIConstant.BASE_VIEW_POINT_RATE;
import static com.example.templet.template.chat.AIConstant.NOT_OVER_FOLL_COEFFICIENT;
import static com.example.templet.template.chat.AIConstant.REACTION_POINT_MAX;
import static com.example.templet.template.chat.AIConstant.SELF_POINT_COEFFICIENT_MAX;

import com.example.templet.model.type.AiEnumPowerModel;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class SymbolicAIService {
  public static Long getSubjectAIPoints(String subjectId, ReactionRepository repository) {
    // todo: repository
    Long totalReactionNumber = repository.count();

    // view point
    long viewPointsReactionNumber = totalReactionNumber + 1;
    long viewBasePoint = NOT_OVER_FOLL_COEFFICIENT / viewPointsReactionNumber;
    ;
    // todo: repository
    long viewNumber =
        repository.countByVisionTrue(subjectId) == null
            ? 1
            : repository.countByVisionTrue(subjectId);
    long viewPoints =
        takeSymbolicAiPoint(
            REACTION_POINT_MAX * BASE_VIEW_POINT_RATE / 100, viewBasePoint, viewNumber);

    // Like point
    long likePointsReactionNumber = totalReactionNumber / 2 + 1;
    long likeBasePoint = NOT_OVER_FOLL_COEFFICIENT / likePointsReactionNumber;
    // todo: repository
    long likeNumber =
        repository.countByLikeReactionLike(subjectId) == null
            ? 1
            : repository.countByLikeReactionLike(subjectId);
    // todo: repository
    long dislikeNumber =
        repository.countByLikeReactionDislike(subjectId) == null
            ? 1
            : repository.countByLikeReactionDislike(subjectId);
    long likePoints =
        takeSymbolicAiPoint(
            REACTION_POINT_MAX * BASE_LIKE_POINT_RATE / 100, likeBasePoint, likeNumber);
    long dislikePoints =
        passesThrough0WithFiniteLimit(
            REACTION_POINT_MAX * BASE_LIKE_POINT_RATE / 200,
            REACTION_POINT_MAX * BASE_LIKE_POINT_RATE / 2000,
            dislikeNumber);
    long likeAndDislikePoint = likePoints - dislikePoints;

    // Stars point
    long starsPointsReactionNumber =
        totalReactionNumber * 3 / 2 + 1; // *3 because ve number most be multiply by the
    long starsBasePoint = NOT_OVER_FOLL_COEFFICIENT / starsPointsReactionNumber;
    // todo: repository
    long startNumber =
        repository.countByStarsNumberNotNull(subjectId) == null
            ? 1
            : repository.countByStarsNumberNotNull(subjectId);
    // todo: repository
    double startAverage =
        repository.averageStarsNumber(subjectId) == null
            ? 2.5
            : repository.averageStarsNumber(subjectId);

    long starsImpact = 1;

    if (startNumber > 10_000_000) {
      starsImpact = 10;
    } else if (startNumber > 1_000_000) {
      starsImpact = 8;
    } else if (startNumber > 100_000) {
      starsImpact = 6;
    } else if (startNumber > 10_000) {
      starsImpact = 4;
    } else if (startNumber > 1_000) {
      starsImpact = 2;
    }

    long starsPoints =
        (takeSymbolicAiPoint(
                    REACTION_POINT_MAX * BASE_STARS_POINT_RATE / 100,
                    starsBasePoint,
                    (startNumber * ((long) (startAverage * 1000)) - 2000) / 1000)
                / 10)
            * starsImpact;

    long point = viewPoints + likeAndDislikePoint + starsPoints;
    if (point >= REACTION_POINT_MAX * BASE_STARS_POINT_RATE / 100) {
      log.info(
          "Attention: the limit has been exceeded at REACTION_POINT_MAX : "
              + REACTION_POINT_MAX * BASE_STARS_POINT_RATE / 100
              + " . the value now is {}",
          point);
    }
    return point;
  }

  public static Long generateUserPersonalPointInOneSubject(
      List<Enum> subjectEumValues, List<AiEnumPowerModel> enumsValuePower) {
    long point = 1;
    for (Enum subjectEumValue : subjectEumValues) {
      for (AiEnumPowerModel aiEnumPower : enumsValuePower) {
        if (aiEnumPower.getAnEnum().name().equals(subjectEumValue.name())) {
          point += aiEnumPower.getPower();
        }
      }
    }
    int subjectValueNumber = subjectEumValues.size();

    long impact = 100;

    if (subjectValueNumber > 10) {
      impact = 400 / subjectValueNumber;
    } else if (subjectValueNumber > 8) {
      impact = 50;
    } else if (subjectValueNumber > 6) {
      impact = 60;
    } else if (subjectValueNumber > 4) {
      impact = 70;
    } else if (subjectValueNumber > 2) {
      impact = 80;
    }
    return point * impact + SELF_POINT_COEFFICIENT_MAX / 2;
  }
}
