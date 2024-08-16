package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.mapper.ProjectCategoryMapper;
import com.example.templet.endpoint.rest.model.ProjectCategory;
import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.service.ProjectCategoryService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class CatogoriesController {
  private final ProjectCategoryService projectCategoryService;
  private final ProjectCategoryMapper mapper;

  @GetMapping(value = "/categories")
  public List<ProjectCategory> getNotifications(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    return projectCategoryService.findAll(pageFromOne, boundedPageSize).stream()
        .map(projectCategory -> mapper.toRest(projectCategory, new ArrayList<>()))
        .toList();
  }
}
