package com.example.templet.endpoint.rest.controller;

import com.example.templet.repository.model.Project;
import com.example.templet.service.ProjectFileervice;
import com.example.templet.template.file.validator.ImageValidator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class ProjectFileController {

  private final ProjectFileervice service;
  private final ImageValidator imageValidator;

  @PutMapping(value = "/projects/{xid}/pictures")
  public Project putUserPicture(
      @PathVariable String xid, @RequestBody(required = false) byte[] pictureData) {
    imageValidator.accept(pictureData);
    return (Project) service.uploadPicture(xid, pictureData);
  }
}
