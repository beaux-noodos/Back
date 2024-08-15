package com.example.templet.endpoint.mapper;

import com.example.templet.endpoint.rest.model.CourseSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CourseSessionMapper {
  private final UserMapper userMapper;
  private final CourseMapper courseMapper;
  private final LocationMapper locationMapper;

  public com.example.templet.repository.model.CourseSession toDomain(CourseSession rest) {
    return com.example.templet.repository.model.CourseSession.builder()
        .id(rest.getId())
        .lastUpdateDatetime(rest.getUpdatedAt())
        .creationDatetime(rest.getCreationDatetime())
        .title(rest.getTitle())
        .description(rest.getDescription())
        .course(courseMapper.toDomain((rest.getCourse()), null))
        .endDatetime(rest.getEndDatetime())
        .startDatetime(rest.getStartDatetime())
        .location(locationMapper.toDomain((rest.getLocation())))
        .professor(userMapper.toDomain((rest.getProfessor())))
        .build();
  }

  public CourseSession toRest(com.example.templet.repository.model.CourseSession domain) {
    return new CourseSession()
        .id(domain.getId())
        .updatedAt(domain.getLastUpdateDatetime())
        .creationDatetime(domain.getCreationDatetime())
        .course(courseMapper.toRest(domain.getCourse(), null))
        .location(locationMapper.toRest(domain.getLocation()))
        .professor(userMapper.toRest(domain.getProfessor()))
        .endDatetime(domain.getEndDatetime())
        .startDatetime(domain.getStartDatetime())
        .description(domain.getDescription())
        .course(courseMapper.toRest(domain.getCourse(), null))
        .title(domain.getTitle())
        .location(locationMapper.toRest(domain.getLocation()));
  }
}
