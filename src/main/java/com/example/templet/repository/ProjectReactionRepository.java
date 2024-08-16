package com.example.templet.repository;

import com.example.templet.repository.model.ProjectReaction;
import com.example.templet.template.sucgestIAWithReaction.ReactionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectReactionRepository
    extends JpaRepository<ProjectReaction, String>, ReactionRepository<ProjectReaction> {

  @Override
  List<ProjectReaction> findAll();

  @Override
  Optional<ProjectReaction> findById(String id);

  @Override
  Optional<ProjectReaction> findAllByUserIdAndSubjectId(String userId, String subjectId);

  @Override
  List<ProjectReaction> findAllBySubjectId(String subjectId, Pageable pageable);

  @Override
  List<ProjectReaction> findAllByUserId(String userId, Pageable pageable);

  @Override
  @Query("SELECT COUNT(r) FROM ProjectReaction r")
  long count();

  @Override
  @Query(
      "SELECT COUNT(r) FROM ProjectReaction r WHERE r.vision = TRUE and r.subject.id = :subject_id")
  Long countByVisionTrue(@Param("subject_id") String subject_id);

  @Override
  @Query(
      "SELECT COUNT(r) FROM ProjectReaction r WHERE r.starsNumber IS NOT NULL and r.subject.id ="
          + " :subject_id")
  Long countByStarsNumberNotNull(@Param("subject_id") String subject_id);

  @Override
  @Query(
      "SELECT COUNT(r) FROM ProjectReaction r WHERE r.likeReaction = 'LIKE' and r.subject.id ="
          + " :subject_id")
  Long countByLikeReactionLike(@Param("subject_id") String subject_id);

  @Override
  @Query(
      "SELECT COUNT(r) FROM ProjectReaction r WHERE r.likeReaction = 'DISLIKE' and r.subject.id ="
          + " :subject_id")
  Long countByLikeReactionDislike(@Param("subject_id") String subject_id);

  @Override
  @Query(
      "SELECT AVG(r.starsNumber) FROM ProjectReaction r WHERE r.starsNumber IS NOT NULL and"
          + " r.subject.id = :subject_id")
  Double averageStarsNumber(@Param("subject_id") String subject_id);
}
