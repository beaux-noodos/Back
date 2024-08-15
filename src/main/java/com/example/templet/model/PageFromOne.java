package com.example.templet.model;

import com.example.templet.model.exception.BadRequestException;
import lombok.Getter;

@Getter
public class PageFromOne {
  public static final int MIN_PAGE = 1;
  private final int value;

  public PageFromOne(int value) {
    if (value < MIN_PAGE) {
      throw new BadRequestException("Page must be >=1");
    }
    this.value = value;
  }
}
