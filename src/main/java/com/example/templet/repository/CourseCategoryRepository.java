package com.example.templet.repository;

import com.example.templet.repository.model.CourseCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, String> {
  @Override
  List<CourseCategory> findAll();

  @Query("SELECT x FROM Course x ")
  List<CourseCategory> findAllWithPage(Pageable pageable);

  @Override
  Optional<CourseCategory> findById(String id);
}
