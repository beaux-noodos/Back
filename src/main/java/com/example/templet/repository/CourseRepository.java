package com.example.templet.repository;

import com.example.templet.repository.model.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
  @Override
  List<Course> findAll();

  @Query("SELECT x FROM Course x ")
  List<Course> findAllWithPage(Pageable pageable);

  @Override
  Optional<Course> findById(String id);

  List<Course> findAllByUserId(String userId, Pageable pageable);

  @Query(
      "SELECT c FROM Course c JOIN c.interestedUsers u where u.id = :userId order by"
          + " c.lastUpdateDatetime")
  List<Course> findAllCoursesInterestedByUserId(String userId, Pageable pageable);

  @Query(
      "SELECT c FROM Course c JOIN c.followers u where u.id = :userId order by"
          + " c.lastUpdateDatetime")
  List<Course> findAllCoursesFollowersByUserId(String userId, Pageable pageable);
}
