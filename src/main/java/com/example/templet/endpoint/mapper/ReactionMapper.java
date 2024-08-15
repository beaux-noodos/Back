package com.example.templet.endpoint.mapper;

import static com.example.templet.service.utils.EnumMapperUtils.mapEnum;

import com.example.templet.endpoint.rest.model.Reaction;
import com.example.templet.endpoint.rest.model.ReactionType;
import com.example.templet.repository.model.CourseReaction;
import com.example.templet.repository.model.CourseSessionReaction;
import com.example.templet.repository.model.LocationReaction;
import com.example.templet.repository.model.NotificationReaction;
import com.example.templet.repository.model.SignalisationReaction;
import com.example.templet.service.UserService;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ReactionMapper {
  private final UserService userService;

  public CourseReaction toDomainCourseReaction(Reaction rest) {
    CourseReaction reaction = new CourseReaction();
    reaction.setId(rest.getId());
    reaction.setLastUpdateDatetime(rest.getLastUpdateDatetime());
    reaction.setCreationDatetime(rest.getCreationDatetime());
    reaction.setComment(rest.getComment());
    reaction.setLikeReaction(convertToDomain(rest.getLikeReaction()));
    reaction.setVision(rest.getVision());
    reaction.setStarsNumber(rest.getStarsNumber());
    reaction.setUser(userService.findById(rest.getUserId()));
    return reaction;
  }

  public CourseSessionReaction toDomainCourseSessionReaction(Reaction rest) {
    CourseSessionReaction reaction = new CourseSessionReaction();
    reaction.setId(rest.getId());
    reaction.setLastUpdateDatetime(rest.getLastUpdateDatetime());
    reaction.setCreationDatetime(rest.getCreationDatetime());
    reaction.setComment(rest.getComment());
    reaction.setLikeReaction(convertToDomain(rest.getLikeReaction()));
    reaction.setVision(rest.getVision());
    reaction.setStarsNumber(rest.getStarsNumber());
    reaction.setUser(userService.findById(rest.getUserId()));
    return reaction;
  }

  public LocationReaction toDomainLocationReaction(Reaction rest) {
    LocationReaction reaction = new LocationReaction();
    reaction.setId(rest.getId());
    reaction.setLastUpdateDatetime(rest.getLastUpdateDatetime());
    reaction.setCreationDatetime(rest.getCreationDatetime());
    reaction.setComment(rest.getComment());
    reaction.setLikeReaction(convertToDomain(rest.getLikeReaction()));
    reaction.setVision(rest.getVision());
    reaction.setStarsNumber(rest.getStarsNumber());
    reaction.setUser(userService.findById(rest.getUserId()));
    return reaction;
  }

  public NotificationReaction toDomainNotificationReaction(Reaction rest) {
    NotificationReaction reaction = new NotificationReaction();
    reaction.setId(rest.getId());
    reaction.setLastUpdateDatetime(rest.getLastUpdateDatetime());
    reaction.setCreationDatetime(rest.getCreationDatetime());
    reaction.setComment(rest.getComment());
    reaction.setLikeReaction(convertToDomain(rest.getLikeReaction()));
    reaction.setVision(rest.getVision());
    reaction.setStarsNumber(rest.getStarsNumber());
    reaction.setUser(userService.findById(rest.getUserId()));
    return reaction;
  }

  public SignalisationReaction toDomainSignalisationReaction(Reaction rest) {
    SignalisationReaction reaction = new SignalisationReaction();
    reaction.setId(rest.getId());
    reaction.setLastUpdateDatetime(rest.getLastUpdateDatetime());
    reaction.setCreationDatetime(rest.getCreationDatetime());
    reaction.setComment(rest.getComment());
    reaction.setLikeReaction(convertToDomain(rest.getLikeReaction()));
    reaction.setVision(rest.getVision());
    reaction.setStarsNumber(rest.getStarsNumber());
    reaction.setUser(userService.findById(rest.getUserId()));
    return reaction;
  }

  // toDomainSignalisationReaction

  public Reaction toRestReaction(
      com.example.templet.template.sucgestIAWithReaction.Reaction domain) {
    return new Reaction()
        .id(domain.getId())
        .lastUpdateDatetime(domain.getLastUpdateDatetime())
        .creationDatetime(domain.getCreationDatetime())
        .comment(domain.getComment())
        .likeReaction(convertToRest(domain.getLikeReaction()))
        .vision(domain.getVision())
        .starsNumber(domain.getStarsNumber())
        .userId(domain.getUser().getId())
        .subjectId(domain.getSubject().getId());
  }

  public static ReactionType convertToRest(
      com.example.templet.model.enums.ReactionType reactionType) {
    return mapEnum(
        reactionType,
        Map.of(
            com.example.templet.model.enums.ReactionType.DISLIKE, ReactionType.DISLIKE,
            com.example.templet.model.enums.ReactionType.LIKE, ReactionType.LIKE));
  }

  public static com.example.templet.model.enums.ReactionType convertToDomain(
      ReactionType reactionType) {
    return mapEnum(
        reactionType,
        Map.of(
            ReactionType.DISLIKE, com.example.templet.model.enums.ReactionType.DISLIKE,
            ReactionType.LIKE, com.example.templet.model.enums.ReactionType.LIKE));
  }
}
