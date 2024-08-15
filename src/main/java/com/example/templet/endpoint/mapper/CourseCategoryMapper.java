package com.example.templet.endpoint.mapper;

import com.example.templet.endpoint.rest.model.Course;
import com.example.templet.endpoint.rest.model.CourseCategory;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CourseCategoryMapper {
  public com.example.templet.repository.model.CourseCategory toDomain(CourseCategory rest) {
    return com.example.templet.repository.model.CourseCategory.builder()
        .id(UUID.randomUUID().toString())
        .name(rest.getName())
        .courses(null)
        .description(rest.getDescription())
        .lastUpdateDatetime(null)
        .creationDatetime(null)
        .build();
  }

  public CourseCategory toRest(
      com.example.templet.repository.model.CourseCategory domain, List<Course> courses) {
    return new CourseCategory().name(domain.getName()).description(domain.getDescription());
  }
}
