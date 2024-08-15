package com.example.templet.template.sucgestIAWithReaction;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.exception.BadRequestException;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.model.validator.ReactionValidator;
import com.example.templet.repository.CourseRepository;
import com.example.templet.repository.CourseSessionRepository;
import com.example.templet.repository.LocationRepository;
import com.example.templet.repository.dao.ReactionDao;
import com.example.templet.repository.model.Course;
import com.example.templet.repository.model.CourseSession;
import com.example.templet.repository.model.Location;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class ReactionService {
  protected final ReactionRepository repository;
  private final ReactionValidator reactionValidator;
  private final ReactionDao reactionDao;
  private final HaveReactionService reactionRepository;
  private final CourseRepository courseRepository;
  private final CourseSessionRepository courseSessionRepository;
  private final LocationRepository locationRepository;

  long updateLikeNumber(Long reaction, String haveReactionId, Boolean isNew) {
    HaveReaction OptionalHaveReaction = reactionRepository.findById(haveReactionId);
    if (OptionalHaveReaction == null) {
      throw new BadRequestException("Object with id " + haveReactionId + " does not exist");
    }
    HaveReaction haveReaction = OptionalHaveReaction;
    if (isNew) {
      haveReaction.setLikeNumber(haveReaction.getLikeNumber() + reaction);
    } else {
      haveReaction.setLikeNumber(haveReaction.getLikeNumber() + reaction);
      haveReaction.setDislikeNumber(haveReaction.getDislikeNumber() - reaction);
    }
    reactionRepository.save(haveReaction);
    return haveReaction.getLikeNumber();
  }

  long updateDislikeNumber(Long reaction, String haveReactionId, Boolean isNew) {
    HaveReaction OptionalHaveReaction = reactionRepository.findById(haveReactionId);
    if (OptionalHaveReaction == null) {
      throw new BadRequestException("Object with id " + haveReactionId + " does not exist");
    }
    HaveReaction haveReaction = OptionalHaveReaction;
    if (isNew) {
      haveReaction.setDislikeNumber(haveReaction.getDislikeNumber() + reaction);
    } else {
      haveReaction.setDislikeNumber(haveReaction.getDislikeNumber() + reaction);
      haveReaction.setLikeNumber(haveReaction.getLikeNumber() - reaction);
    }
    reactionRepository.save(haveReaction);
    return haveReaction.getDislikeNumber();
  }

  long updateViewNumber(Long reaction, String haveReactionId, Boolean isNew) {
    HaveReaction OptionalHaveReaction = reactionRepository.findById(haveReactionId);
    if (OptionalHaveReaction == null) {
      throw new BadRequestException("Object with id " + haveReactionId + " does not exist");
    }
    HaveReaction haveReaction = OptionalHaveReaction;
    if (isNew == null) {
      haveReaction.setViewNumber(haveReaction.getViewNumber() + reaction);
    }
    reactionRepository.save(haveReaction);
    return haveReaction.getViewNumber();
  }

  double updateStarMedium(double reaction, String haveReactionId, Boolean isNew) {
    HaveReaction OptionalHaveReaction = reactionRepository.findById(haveReactionId);
    if (OptionalHaveReaction == null) {
      throw new BadRequestException("Object with id " + haveReactionId + " does not exist");
    }
    HaveReaction haveReaction = OptionalHaveReaction;
    double newStarMedium =
        (haveReaction.getStarMedium() * haveReaction.getStarNumber() + reaction)
            / (haveReaction.getStarNumber() + 1);

    if (isNew) {
      haveReaction.setStarMedium(newStarMedium);
      haveReaction.setStarNumber(haveReaction.getStarNumber() + 1);
    } else {
      haveReaction.setStarMedium(newStarMedium);
      haveReaction.setStarNumber(haveReaction.getStarNumber() + 1);
    }

    if (haveReaction.getClass().equals(Course.class)) {
      Course course = (Course) haveReaction;

      courseRepository.save(course);

    } else if (haveReaction.getClass().equals(CourseSession.class)) {
      courseSessionRepository.save((CourseSession) haveReaction);
      courseSessionRepository.save((CourseSession) haveReaction);

    } else if (haveReaction.getClass().equals(Location.class)) {
      locationRepository.save((Location) haveReaction);
      locationRepository.save((Location) haveReaction);
    }
    return haveReaction.getStarMedium();
  }

  public List<Reaction> findAll() {
    return repository.findAll();
  }

  @Transactional
  public Reaction save(Reaction toSave) {
    reactionValidator.accept(toSave);
    return repository.save(toSave);
  }

  @Transactional
  public Reaction crupdateReaction(Reaction reaction, String reactionId) {
    reactionValidator.accept(reaction);
    if (reactionId != null) {
      Optional<Reaction> reactionOptional = repository.findById(reactionId);
      if (reactionOptional.isPresent()) {
        Reaction reactionFromDomain = reactionOptional.get();
        if (reaction.getUser() == null) {
          reaction.setUser(reactionFromDomain.getUser());
        }
        reaction.setCreationDatetime(reactionFromDomain.getCreationDatetime());
      }
    }
    if (reaction.getCreationDatetime() == null) {
      reaction.setCreationDatetime(Instant.now());
    }
    reaction.setLastUpdateDatetime(Instant.now());
    return save(reaction);
  }

  public Reaction findById(String id) {
    Optional<Reaction> reactionOptional = repository.findById(id);
    if (reactionOptional.isEmpty()) {
      throw new NotFoundException("Reaction with id " + id + " not found");
    }
    return reactionOptional.get();
  }

  public Boolean checkSelfReaction(String reactionId, String reactionIdFromEndpoint) {
    Optional<Reaction> reactionOptional = repository.findById(reactionIdFromEndpoint);
    if (reactionOptional.isEmpty()) {
      return true;
    }
    Reaction reaction = reactionOptional.get();
    return reaction.getId().equals(reactionId);
  }

  public List<Reaction> findAllByUserId(String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllByUserId(userId, pageable);
  }

  public List<Reaction> findAllBySubjectId(
      String subjectId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllBySubjectId(subjectId, pageable);
  }

  @Transactional
  public Reaction react(String userId, String subjectId, Reaction reaction) {
    Optional<Reaction> reactionOptional = repository.findAllByUserIdAndSubjectId(userId, subjectId);

    if (reactionOptional.isPresent()) {
      Reaction actualReaction = reactionOptional.get();
      if (!actualReaction.getId().equals(reaction.getId())) {
        throw new BadRequestException("id mismatch");
      }
      if (reaction.getLikeReaction() == null) {
        reaction.setLikeReaction(actualReaction.getLikeReaction());
      }
      if (reaction.getComment() == null) {
        reaction.setComment(actualReaction.getComment());
      }
      if (reaction.getStarsNumber() == null) {
        reaction.setStarsNumber(actualReaction.getStarsNumber());
      }
      if (reaction.getVision() == null) {
        reaction.setVision(actualReaction.getVision());
      }
    }
    return save(reaction);
  }

  public List getReactionsByCriteria(
      String subjectId,
      String userId,
      Boolean havelikeReaction,
      Boolean haveVision,
      Boolean haveStarsNumber,
      Boolean haveComment,
      PageFromOne page,
      BoundedPageSize pageSize,
      Class clazz) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return reactionDao.findByCriteria(
        subjectId,
        userId,
        havelikeReaction,
        haveVision,
        haveStarsNumber,
        haveComment,
        pageable,
        clazz);
  }
}
