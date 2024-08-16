package com.example.templet.endpoint.mapper;

import static com.example.templet.service.utils.EnumMapperUtils.mapEnum;

import com.example.templet.endpoint.rest.model.Project;
import com.example.templet.endpoint.rest.model.StatusEnum;
import com.example.templet.model.enums.ProjectStatus;
import com.example.templet.repository.model.ProjectSession;
import com.example.templet.service.ProjectFileervice;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProjectMapper {
  private final UserMapper userMapper;
  private final ProjectCategoryMapper projectCategoryMapper;
  private final ProjectFileervice projectFileervice;
  private final LocationMapper locationMapper;

  public com.example.templet.repository.model.Project toDomain(
      Project rest, List<ProjectSession> projectSessions) {
    return com.example.templet.repository.model.Project.builder()
        .id(rest.getId())
        .lastUpdateDatetime(rest.getUpdatedAt())
        .creationDatetime(rest.getCreationDatetime())
        .user(userMapper.toDomain((rest.getUser())))
        .price(rest.getPrice())
        .title(rest.getTitle())
        .projectCategorise(
            rest.getCategories().stream().map(projectCategoryMapper::toDomain).toList())
        .description(rest.getDescription())
        .imageKey(projectFileervice.getKey(rest.getId()))
        .pictureIsImplemented(Boolean.TRUE.equals(rest.getPictureIsImplemented()))
        .technicalSolution(UserMapper.toDomain(rest.getTechnicalSolution()))
        .investor(UserMapper.toDomain(rest.getInvestor()))
        .sessions(projectSessions)
        .projectStatus(toDomain(rest.getStatus()))
        .startDatetime(rest.getStartDatetime() == null ? null : rest.getStartDatetime())
        .endDatetime(rest.getEndDatetime() == null ? null : rest.getEndDatetime())
        .investorNeed(rest.getNeedInvestor())
        .technicalSolutionNeed(rest.getNeedTechnicalSolution())
        .localisation(locationMapper.toDomain(rest.getLocalisation()))
        .build();
  }

  public Project toRest(
      com.example.templet.repository.model.Project domain,
      List<com.example.templet.endpoint.rest.model.ProjectSession> projectSessions) {
    return new Project()
        .id(domain.getId())
        .updatedAt(domain.getLastUpdateDatetime())
        .creationDatetime(domain.getCreationDatetime())
        .user(userMapper.toRest(domain.getUser()))
        .price((float) domain.getPrice())
        .title(domain.getTitle())
        .categories(
            domain.getProjectCategorise().stream()
                .map(projectCategory -> projectCategoryMapper.toRest(projectCategory, null))
                .toList())
        .description(domain.getDescription())
        .imageUrl(projectFileervice.getUrl(domain.getId()))
        .investor(userMapper.toRest(domain.getInvestor()))
        .sessions(projectSessions)
        .pictureIsImplemented(domain.getPictureIsImplemented())
        .status(convertToRest(domain.getProjectStatus()))
        .technicalSolution(userMapper.toRest(domain.getTechnicalSolution()))
        .investor(userMapper.toRest(domain.getInvestor()))
        .sessions(projectSessions)
        .status(convertToRest(domain.getProjectStatus()))
        .startDatetime(domain.getStartDatetime() == null ? null : domain.getStartDatetime())
        .endDatetime(domain.getEndDatetime() == null ? null : domain.getEndDatetime())
        .needTechnicalSolution(domain.isTechnicalSolutionNeed())
        .needInvestor(domain.isInvestorNeed())
        .localisation(locationMapper.toRest(domain.getLocalisation()));
  }

  public static StatusEnum convertToRest(com.example.templet.model.enums.ProjectStatus domain) {
    if (domain == null) {
      return null;
    }
    return mapEnum(
        domain,
        Map.of(
            ProjectStatus.COMPLETED, StatusEnum.COMPLETED,
            ProjectStatus.CONFIRMED, StatusEnum.CONFIRMED,
            ProjectStatus.IN_PROGRESS, StatusEnum.IN_PROGRESS,
            ProjectStatus.PLANNING, StatusEnum.PLANNING));
  }

  public static com.example.templet.model.enums.ProjectStatus toDomain(StatusEnum rest) {
    if (rest == null) {
      return null;
    }
    return mapEnum(
        rest,
        Map.of(
            StatusEnum.COMPLETED, com.example.templet.model.enums.ProjectStatus.COMPLETED,
            StatusEnum.CONFIRMED, com.example.templet.model.enums.ProjectStatus.CONFIRMED,
            StatusEnum.IN_PROGRESS, com.example.templet.model.enums.ProjectStatus.IN_PROGRESS,
            StatusEnum.PLANNING, com.example.templet.model.enums.ProjectStatus.PLANNING));
  }
}
