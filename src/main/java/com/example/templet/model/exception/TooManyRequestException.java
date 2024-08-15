package com.example.templet.model.exception;

import static com.example.templet.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

public class TooManyRequestException extends ApiException {
  public TooManyRequestException(String message) {
    super(SERVER_EXCEPTION, message);
  }
}
