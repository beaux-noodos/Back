package com.example.templet.endpoint.rest.controller;

import static com.example.templet.service.utils.EnumMapperUtils.mapEnum;

import com.example.templet.endpoint.mapper.CourseMapper;
import com.example.templet.endpoint.mapper.CourseSessionMapper;
import com.example.templet.endpoint.rest.model.ActionEnum;
import com.example.templet.endpoint.rest.model.Course;
import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.CourseService;
import com.example.templet.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class UserFolowingController {
  private final JwtUtils jwtUtils;
  private final CourseService courseService;
  private final CourseMapper courseMapper;
  private final CourseSessionMapper courseSessionMapper;
  private final UserService userService;

  @GetMapping(value = "/users/{uid}/interested/courses")
  public List<Course> findAllCoursesInterestedByUserId(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      @PathVariable(name = "uid") String userId) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    return courseService
        .findAllCoursesInterestedByUserId(userId, pageFromOne, boundedPageSize)
        .stream()
        .map(
            course ->
                courseMapper.toRest(
                    course,
                    course.getSessions().stream().map(courseSessionMapper::toRest).toList()))
        .toList();
  }

  @GetMapping(value = "/users/{uid}/subscribe/courses")
  public List<Course> findAllCoursesFollowersByUserId(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      @PathVariable(name = "uid") String userId) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    return courseService
        .findAllCoursesFollowersByUserId(userId, pageFromOne, boundedPageSize)
        .stream()
        .map(
            course ->
                courseMapper.toRest(
                    course,
                    course.getSessions().stream().map(courseSessionMapper::toRest).toList()))
        .toList();
  }

  @PutMapping(value = "/users/{id}/courses/{cid}/follow/{follow_action}")
  public Course findAllCoursesFollowersByUserId(
      @PathVariable(name = "id") String userId,
      @PathVariable(name = "cid") String courseId,
      @PathVariable(name = "follow_action") ActionEnum folowAction) {
    com.example.templet.model.enums.ActionEnum actionEnum =
        mapEnum(com.example.templet.model.enums.ActionEnum.class, folowAction);
    com.example.templet.repository.model.Course course =
        userService.userFolowAction(userId, courseId, actionEnum);
    return courseMapper.toRest(
        course, course.getSessions().stream().map(courseSessionMapper::toRest).toList());
  }
}
