package com.example.templet.repository;

import com.example.templet.repository.model.CourseSession;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession, String> {

  @Override
  List<CourseSession> findAll();

  @Override
  Optional<CourseSession> findById(String id);

  @Query("SELECT y FROM CourseSession y ")
  List<CourseSession> findAllWithPage(Pageable pageable);

  List<CourseSession> findAllByCourseId(String courseId, Pageable pageable);

  @Query(
      "SELECT DISTINCT cs FROM CourseSession cs "
          + "JOIN cs.course c "
          + "JOIN c.followers f "
          + // Jointure sur la collection de followers
          "WHERE f.id = :userId "
          + "ORDER BY cs.startDatetime DESC")
  List<CourseSession> getUsersCourseSessions(String userId, Pageable pageable);

  List<CourseSession> findAllByLocationId(String locationzId, Pageable pageable);
}
