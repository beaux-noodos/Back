package com.example.templet.repository;

import com.example.templet.repository.model.ProjectSession;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectSessionRepository extends JpaRepository<ProjectSession, String> {

  @Override
  List<ProjectSession> findAll();

  @Override
  Optional<ProjectSession> findById(String id);

  @Query("SELECT y FROM ProjectSession y ")
  List<ProjectSession> findAllWithPage(Pageable pageable);

  List<ProjectSession> findAllByProjectId(String projectId, Pageable pageable);

  @Query(
      "SELECT DISTINCT cs FROM ProjectSession cs "
          + "JOIN cs.project p "
          + "JOIN p.investor i "
          + "WHERE i.id = :userId "
          + "ORDER BY cs.endDatetime DESC")
  List<ProjectSession> getUsersProjectSessions(String userId, Pageable pageable);

  List<ProjectSession> findAllByLocationId(String locationzId, Pageable pageable);
}
