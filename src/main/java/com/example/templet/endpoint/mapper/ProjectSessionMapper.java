package com.example.templet.endpoint.mapper;

import com.example.templet.endpoint.rest.model.ProjectSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProjectSessionMapper {
  private final UserMapper userMapper;
  private final ProjectMapper projectMapper;
  private final LocationMapper locationMapper;

  public com.example.templet.repository.model.ProjectSession toDomain(ProjectSession rest) {
    return com.example.templet.repository.model.ProjectSession.builder()
        .id(rest.getId())
        .lastUpdateDatetime(rest.getUpdatedAt())
        .creationDatetime(rest.getCreationDatetime())
        .title(rest.getTitle())
        .description(rest.getDescription())
        .project(projectMapper.toDomain((rest.getProject()), null))
        .endDatetime(rest.getEndDatetime())
        .location(locationMapper.toDomain((rest.getLocation())))
        .build();
  }

  public ProjectSession toRest(com.example.templet.repository.model.ProjectSession domain) {
    return new ProjectSession()
        .id(domain.getId())
        .updatedAt(domain.getLastUpdateDatetime())
        .creationDatetime(domain.getCreationDatetime())
        .project(projectMapper.toRest(domain.getProject(), null))
        .location(locationMapper.toRest(domain.getLocation()))
        .endDatetime(domain.getEndDatetime())
        .description(domain.getDescription())
        .project(projectMapper.toRest(domain.getProject(), null))
        .title(domain.getTitle())
        .location(locationMapper.toRest(domain.getLocation()));
  }
}
