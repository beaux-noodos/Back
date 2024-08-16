package com.example.templet.endpoint.mapper;

import com.example.templet.endpoint.rest.model.Project;
import com.example.templet.endpoint.rest.model.ProjectCategory;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProjectCategoryMapper {
  public com.example.templet.repository.model.ProjectCategory toDomain(ProjectCategory rest) {
    return com.example.templet.repository.model.ProjectCategory.builder()
        .id(UUID.randomUUID().toString())
        .name(rest.getName())
        .projects(null)
        .description(rest.getDescription())
        .lastUpdateDatetime(null)
        .creationDatetime(null)
        .build();
  }

  public ProjectCategory toRest(
      com.example.templet.repository.model.ProjectCategory domain, List<Project> projects) {
    return new ProjectCategory().name(domain.getName()).description(domain.getDescription());
  }
}
