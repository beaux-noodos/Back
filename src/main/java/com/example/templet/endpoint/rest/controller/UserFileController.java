package com.example.templet.endpoint.rest.controller;

import static com.example.templet.endpoint.validator.RequestInputValidator.InputType.QUERY_PARAMS;

import com.example.templet.endpoint.rest.model.UserPicture;
import com.example.templet.endpoint.rest.model.UserPictureType;
import com.example.templet.endpoint.validator.RequestInputValidator;
import com.example.templet.service.UserFileService;
import com.example.templet.template.file.validator.ImageValidator;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class UserFileController {

  private final UserFileService service;
  private final RequestInputValidator requestInputValidator;
  private final ImageValidator imageValidator;

  @PutMapping(value = "/users/{uid}/pictures")
  public UserPicture putUserPicture(
      @PathVariable String uid,
      @RequestParam(value = "type", required = false) UserPictureType type,
      @RequestBody(required = false) byte[] pictureData)
      throws IOException {
    requestInputValidator.notNullValue(QUERY_PARAMS, "type", type);
    imageValidator.accept(pictureData);
    return service.uploadUserPicture(uid, type, pictureData);
  }

  @GetMapping(value = "/users/{uid}/pictures")
  public UserPicture getUserPicture(
      @PathVariable String uid,
      @RequestParam(value = "type", required = false) UserPictureType type) {
    requestInputValidator.notNullValue(QUERY_PARAMS, "type", type);
    return service.getUserPicture(uid, type);
  }
}
