package com.example.templet.endpoint.rest.controller;

import com.example.templet.repository.model.Course;
import com.example.templet.service.CourseFileervice;
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
public class CourseFileController {

  private final CourseFileervice service;
  private final ImageValidator imageValidator;

  @PutMapping(value = "/courses/{xid}/pictures")
  public Course putUserPicture(
      @PathVariable String xid, @RequestBody(required = false) byte[] pictureData) {
    imageValidator.accept(pictureData);
    return (Course) service.uploadPicture(xid, pictureData);
  }
}
