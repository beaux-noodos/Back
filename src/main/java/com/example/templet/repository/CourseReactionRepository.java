package com.example.templet.repository;

import com.example.templet.repository.model.CourseReaction;
import com.example.templet.template.sucgestIAWithReaction.ReactionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseReactionRepository
    extends JpaRepository<CourseReaction, String>, ReactionRepository<CourseReaction> {

  @Override
  List<CourseReaction> findAll();

  @Override
  Optional<CourseReaction> findById(String id);

  @Override
  Optional<CourseReaction> findAllByUserIdAndSubjectId(String userId, String subjectId);

  @Override
  List<CourseReaction> findAllBySubjectId(String subjectId, Pageable pageable);

  @Override
  List<CourseReaction> findAllByUserId(String userId, Pageable pageable);

  @Override
  @Query("SELECT COUNT(r) FROM CourseReaction r")
  long count();

  @Override
  @Query(
      "SELECT COUNT(r) FROM CourseReaction r WHERE r.vision = TRUE and r.subject.id = :subject_id")
  Long countByVisionTrue(@Param("subject_id") String subject_id);

  @Override
  @Query(
      "SELECT COUNT(r) FROM CourseReaction r WHERE r.starsNumber IS NOT NULL and r.subject.id ="
          + " :subject_id")
  Long countByStarsNumberNotNull(@Param("subject_id") String subject_id);

  @Override
  @Query(
      "SELECT COUNT(r) FROM CourseReaction r WHERE r.likeReaction = 'LIKE' and r.subject.id ="
          + " :subject_id")
  Long countByLikeReactionLike(@Param("subject_id") String subject_id);

  @Override
  @Query(
      "SELECT COUNT(r) FROM CourseReaction r WHERE r.likeReaction = 'DISLIKE' and r.subject.id ="
          + " :subject_id")
  Long countByLikeReactionDislike(@Param("subject_id") String subject_id);

  @Override
  @Query(
      "SELECT AVG(r.starsNumber) FROM CourseReaction r WHERE r.starsNumber IS NOT NULL and"
          + " r.subject.id = :subject_id")
  Double averageStarsNumber(@Param("subject_id") String subject_id);
}
