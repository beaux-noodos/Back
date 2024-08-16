package com.example.templet.endpoint.mapper;

import static com.example.templet.service.utils.EnumMapperUtils.mapEnum;

import com.example.templet.endpoint.rest.model.Location;
import com.example.templet.endpoint.rest.model.StatusEnum;
import com.example.templet.model.enums.ProjectStatus;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LocationMapper {

  public com.example.templet.repository.model.Location toDomain(Location rest) {
    return com.example.templet.repository.model.Location.builder()
        .id(rest.getId())
        .lastUpdateDatetime(rest.getUpdatedAt())
        .creationDatetime(rest.getCreationDatetime())
        .name(rest.getName())
        .description(rest.getLatitude())
        .latitude(rest.getLatitude())
        .longitude(rest.getLongitude())
        .build();
  }

  public Location toRest(com.example.templet.repository.model.Location domain) {
    return new Location()
        .id(domain.getId())
        .updatedAt(domain.getLastUpdateDatetime())
        .creationDatetime(domain.getCreationDatetime())
        .name(domain.getName())
        .description(domain.getLatitude())
        .latitude(domain.getLatitude())
        .longitude(domain.getLongitude());
  }

  public static StatusEnum convertToRest(ProjectStatus domain) {
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

  public static ProjectStatus toDomain(StatusEnum rest) {
    if (rest == null) {
      return null;
    }
    return mapEnum(
        rest,
        Map.of(
            StatusEnum.COMPLETED, ProjectStatus.COMPLETED,
            StatusEnum.CONFIRMED, ProjectStatus.CONFIRMED,
            StatusEnum.IN_PROGRESS, ProjectStatus.IN_PROGRESS,
            StatusEnum.PLANNING, ProjectStatus.PLANNING));
  }
}
