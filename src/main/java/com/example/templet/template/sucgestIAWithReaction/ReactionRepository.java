package com.example.templet.template.sucgestIAWithReaction;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ReactionRepository<T extends Reaction> {
  List<T> findAll();

  Optional<T> findById(String id);

  Optional<T> findAllByUserIdAndSubjectId(String userId, String subjectId);

  List<T> findAllBySubjectId(String subjectId, Pageable pageable);

  List<T> findAllByUserId(String userId, Pageable pageable);

  T save(T reaction);

  long count();

  Long countByVisionTrue(String subjectId);

  Long countByStarsNumberNotNull(String subjectId);

  Long countByLikeReactionLike(String subjectId);

  Long countByLikeReactionDislike(String subjectId);

  Double averageStarsNumber(String subjectId);
}
