package com.example.templet.service;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.repository.CourseCategoryRepository;
import com.example.templet.repository.model.CourseCategory;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CourseCategoryService {
  private final CourseCategoryRepository repository;

  public List<CourseCategory> findAll() {
    return repository.findAll();
  }

  public List<CourseCategory> findAll(PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllWithPage(pageable);
  }

  @Transactional
  public CourseCategory save(CourseCategory toSave) {
    return repository.save(toSave);
  }

  public CourseCategory findById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("CourseCategory with id " + id + " not found"));
  }

  public Boolean checkSelfCourse(String courseCategoryId, String courseIdFromEndpoint) {
    Optional<CourseCategory> courseCategoryOptional = repository.findById(courseIdFromEndpoint);
    if (courseCategoryOptional.isEmpty()) {
      return true;
    }
    CourseCategory courseCategory = courseCategoryOptional.get();
    return courseCategory.getId().equals(courseCategoryId);
  }
}
