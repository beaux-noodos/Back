package com.example.templet.repository;

import com.example.templet.repository.model.ProjectCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, String> {
  @Override
  List<ProjectCategory> findAll();

  @Query("SELECT x FROM ProjectCategory x ")
  List<ProjectCategory> findAllWithPage(Pageable pageable);

  @Override
  Optional<ProjectCategory> findById(String id);
}
