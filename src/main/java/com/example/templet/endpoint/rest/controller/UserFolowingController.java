package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.mapper.ProjectMapper;
import com.example.templet.endpoint.mapper.ProjectSessionMapper;
import com.example.templet.endpoint.rest.model.Project;
import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.ProjectService;
import com.example.templet.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class UserFolowingController {
  private final JwtUtils jwtUtils;
  private final ProjectService projectService;
  private final ProjectMapper projectMapper;
  private final ProjectSessionMapper projectSessionMapper;
  private final UserService userService;

  @GetMapping(value = "/users/{uid}/invested/projects")
  public List<Project> findAllProjectsInterestedByUserId(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      @PathVariable(name = "uid") String userId) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    return projectService
        .findAllProjectsInvestedInByUserId(userId, pageFromOne, boundedPageSize)
        .stream()
        .map(
            project ->
                projectMapper.toRest(
                    project,
                    project.getSessions().stream().map(projectSessionMapper::toRest).toList()))
        .toList();
  }

  @GetMapping(value = "/users/{uid}/solve/projects")
  public List<Project> findAllProjectsFollowersByUserId(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      @PathVariable(name = "uid") String userId) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    return projectService
        .findAllProjectsSolvedByUserId(userId, pageFromOne, boundedPageSize)
        .stream()
        .map(
            project ->
                projectMapper.toRest(
                    project,
                    project.getSessions().stream().map(projectSessionMapper::toRest).toList()))
        .toList();
  }

  //  @PutMapping(value = "/users/{id}/projects/{cid}/follow/{follow_action}")
  //  public Project findAllProjectsFollowersByUserId(
  //      @PathVariable(name = "id") String userId,
  //      @PathVariable(name = "cid") String projectId,
  //      @PathVariable(name = "follow_action") ActionEnum folowAction) {
  //    com.example.templet.model.enums.ActionEnum actionEnum =
  //        mapEnum(com.example.templet.model.enums.ActionEnum.class, folowAction);
  //    com.example.templet.repository.model.Project project =
  //        userService.userFolowAction(userId, projectId, actionEnum);
  //    return projectMapper.toRest(
  //        project, project.getSessions().stream().map(projectSessionMapper::toRest).toList());
  //  }
}
