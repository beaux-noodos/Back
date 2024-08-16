package com.example.templet.repository;

import com.example.templet.repository.model.ProjectReaction;
import com.example.templet.template.sucgestIAWithReaction.Reaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepositoryData extends JpaRepository<Reaction, String> {
  @Override
  List<Reaction> findAll();

  @Override
  Optional<Reaction> findById(String id);

  Optional<ProjectReaction> findAllByUserIdAndSubjectId(String userId, String subjectId);

  List<ProjectReaction> findAllBySubjectId(String subjectId, Pageable pageable);

  List<ProjectReaction> findAllByUserId(String userId, Pageable pageable);
}
