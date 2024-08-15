package com.example.templet.endpoint.mapper;

import static com.example.templet.service.utils.EnumMapperUtils.mapEnum;

import com.example.templet.endpoint.rest.model.Course;
import com.example.templet.endpoint.rest.model.StatusEnum;
import com.example.templet.model.enums.CourseStatus;
import com.example.templet.repository.model.CourseSession;
import com.example.templet.service.CourseFileervice;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CourseMapper {
  private final UserMapper userMapper;
  private final CourseCategoryMapper courseCategoryMapper;
  private final CourseFileervice courseFileervice;

  public com.example.templet.repository.model.Course toDomain(
      Course rest, List<CourseSession> courseSessions) {
    return com.example.templet.repository.model.Course.builder()
        .id(rest.getId())
        .lastUpdateDatetime(rest.getUpdatedAt())
        .creationDatetime(rest.getCreationDatetime())
        .user(userMapper.toDomain((rest.getUser())))
        .price(rest.getPrice())
        .title(rest.getTitle())
        .courseCategorise(
            rest.getCategories().stream().map(courseCategoryMapper::toDomain).toList())
        .description(rest.getDescription())
        .followers(rest.getFollowers().stream().map(UserMapper::toDomain).toList())
        .imageKey(courseFileervice.getKey(rest.getId()))
        .pictureIsImplemented(Boolean.TRUE.equals(rest.getPictureIsImplemented()))
        .interestedUsers(rest.getInterestedUsers().stream().map(UserMapper::toDomain).toList())
        .sessions(courseSessions)
        .courseStatus(toDomain(rest.getStatus()))
        .build();
  }

  public Course toRest(
      com.example.templet.repository.model.Course domain,
      List<com.example.templet.endpoint.rest.model.CourseSession> courseSessions) {
    return new Course()
        .id(domain.getId())
        .updatedAt(domain.getLastUpdateDatetime())
        .creationDatetime(domain.getCreationDatetime())
        .user(userMapper.toRest(domain.getUser()))
        .price((float) domain.getPrice())
        .title(domain.getTitle())
        .categories(
            domain.getCourseCategorise().stream()
                .map(courseCategory -> courseCategoryMapper.toRest(courseCategory, null))
                .toList())
        .description(domain.getDescription())
        .followers(domain.getFollowers().stream().map(userMapper::toRest).toList())
        .imageUrl(courseFileervice.getUrl(domain.getId()))
        .interestedUsers(domain.getInterestedUsers().stream().map(userMapper::toRest).toList())
        .sessions(courseSessions)
        .pictureIsImplemented(domain.getPictureIsImplemented())
        .status(convertToRest(domain.getCourseStatus()));
  }

  public static StatusEnum convertToRest(com.example.templet.model.enums.CourseStatus domain) {
    if (domain == null) {
      return null;
    }
    return mapEnum(
        domain,
        Map.of(
            CourseStatus.COMPLETED, StatusEnum.COMPLETED,
            CourseStatus.CONFIRMED, StatusEnum.CONFIRMED,
            CourseStatus.IN_PROGRESS, StatusEnum.IN_PROGRESS,
            CourseStatus.PLANNING, StatusEnum.PLANNING));
  }

  public static com.example.templet.model.enums.CourseStatus toDomain(StatusEnum rest) {
    if (rest == null) {
      return null;
    }
    return mapEnum(
        rest,
        Map.of(
            StatusEnum.COMPLETED, com.example.templet.model.enums.CourseStatus.COMPLETED,
            StatusEnum.CONFIRMED, com.example.templet.model.enums.CourseStatus.CONFIRMED,
            StatusEnum.IN_PROGRESS, com.example.templet.model.enums.CourseStatus.IN_PROGRESS,
            StatusEnum.PLANNING, com.example.templet.model.enums.CourseStatus.PLANNING));
  }
}
