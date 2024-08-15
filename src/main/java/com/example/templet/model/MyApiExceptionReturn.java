package com.example.templet.model;

public class MyApiExceptionReturn {
  private final String type;
  private final String message;

  public MyApiExceptionReturn(String type, String message) {
    this.message = message;
    this.type = type;
  }
}
