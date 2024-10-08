package com.example.templet.model;

import com.example.templet.model.exception.BadRequestException;
import lombok.Getter;

@Getter
public class BoundedPageSize {
  public static final int MAX_SIZE = 500;
  private final int value;

  public BoundedPageSize(int value) {
    if (value < 1) {
      throw new BadRequestException("Page size must be >=1");
    }
    if (value > MAX_SIZE) {
      throw new BadRequestException("Page size must be <" + MAX_SIZE);
    }
    this.value = value;
  }
}
