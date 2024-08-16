package com.example.templet.service;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.repository.ProjectCategoryRepository;
import com.example.templet.repository.model.ProjectCategory;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProjectCategoryService {
  private final ProjectCategoryRepository repository;

  public List<ProjectCategory> findAll() {
    return repository.findAll();
  }

  public List<ProjectCategory> findAll(PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllWithPage(pageable);
  }

  @Transactional
  public ProjectCategory save(ProjectCategory toSave) {
    return repository.save(toSave);
  }

  public ProjectCategory findById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("ProjectCategory with id " + id + " not found"));
  }

  public Boolean checkSelfProject(String projectCategoryId, String projectIdFromEndpoint) {
    Optional<ProjectCategory> projectCategoryOptional = repository.findById(projectIdFromEndpoint);
    if (projectCategoryOptional.isEmpty()) {
      return true;
    }
    ProjectCategory projectCategory = projectCategoryOptional.get();
    return projectCategory.getId().equals(projectCategoryId);
  }
}
