package com.example.templet.repository;

import com.example.templet.repository.model.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
  @Override
  List<Project> findAll();

  @Query("SELECT x FROM Project x ")
  List<Project> findAllWithPage(Pageable pageable);

  @Override
  Optional<Project> findById(String id);

  List<Project> findAllByUserId(String userId, Pageable pageable);

  @Query(
      "SELECT c FROM Project c JOIN c.investor u where u.id = :userId order by"
          + " c.lastUpdateDatetime")
  List<Project> findAllProjectsInvestedInByUserId(String userId, Pageable pageable);

  @Query(
      "SELECT c FROM Project c JOIN c.technicalSolution u where u.id = :userId order by"
          + " c.lastUpdateDatetime")
  List<Project> findAllProjectsSolvedByUserId(String userId, Pageable pageable);
}
