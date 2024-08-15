package com.example.templet.endpoint.rest;

import com.example.templet.model.exception.BadRequestException;
import com.example.templet.model.exception.ForbiddenException;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.model.exception.TooManyRequestException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class InternalToRestExceptionHandler {

  @ExceptionHandler(value = {BadRequestException.class})
  ResponseEntity<Object> handleBadRequest(BadRequestException e) {
    log.info("Bad request", e);
    final Map<String, Object> body = new HashMap<>();
    body.put(
        "type",
        e.getClass()
            .getCanonicalName()
            .split("\\.")[e.getClass().getCanonicalName().split("\\.").length - 1]);
    body.put("message", e.getMessage());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {ForbiddenException.class})
  ResponseEntity<Object> handleForbidden(ForbiddenException e) {
    log.info("Forbidden request", e);
    final Map<String, Object> body = new HashMap<>();
    body.put(
        "type",
        e.getClass()
            .getCanonicalName()
            .split("\\.")[e.getClass().getCanonicalName().split("\\.").length - 1]);
    body.put("message", e.getMessage());

    return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(value = {TooManyRequestException.class})
  ResponseEntity<Object> handleTooManyRequests(TooManyRequestException e) {
    log.info("Too many requests", e);
    final Map<String, Object> body = new HashMap<>();
    body.put(
        "type",
        e.getClass()
            .getCanonicalName()
            .split("\\.")[e.getClass().getCanonicalName().split("\\.").length - 1]);
    body.put("message", e.getMessage());
    return new ResponseEntity<>(body, HttpStatus.TOO_MANY_REQUESTS);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  ResponseEntity<Object> handleNotFound(NotFoundException e) {
    log.info("Not found", e);
    final Map<String, Object> body = new HashMap<>();
    body.put(
        "type",
        e.getClass()
            .getCanonicalName()
            .split("\\.")[e.getClass().getCanonicalName().split("\\.").length - 1]);
    body.put("message", e.getMessage());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  private Exception toRest(Exception e) {
    var restException = new Exception(e.getMessage(), e.getCause());
    return restException;
  }
}
