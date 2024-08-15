package com.example.templet.endpoint.mapper;

import com.example.templet.endpoint.rest.model.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LocationMapper {
  private final UserMapper userMapper;

  public com.example.templet.repository.model.Location toDomain(Location rest) {
    return com.example.templet.repository.model.Location.builder()
        .id(rest.getId())
        .lastUpdateDatetime(rest.getUpdatedAt())
        .creationDatetime(rest.getCreationDatetime())
        .description(rest.getDescription())
        .latitude(rest.getLatitude())
        .longitude(rest.getLongitude())
        .name(rest.getName())
        .build();
  }

  public Location toRest(com.example.templet.repository.model.Location domain) {
    return new Location()
        .id(domain.getId())
        .updatedAt(domain.getLastUpdateDatetime())
        .creationDatetime(domain.getCreationDatetime())
        .description(domain.getDescription())
        .latitude(domain.getLatitude())
        .longitude(domain.getLongitude())
        .name(domain.getName());
  }
}
