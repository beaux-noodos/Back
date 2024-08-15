package com.example.templet.template.file.validator;

import com.example.templet.model.exception.BadRequestException;
import com.example.templet.template.file.validator.utils.ByteArrayTypeValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ImageValidator {
  private final ByteArrayTypeValidator byteArrayTypeValidator;

  public static final int FILE_MAX_SIZE_AS_MB = 2;

  public void accept(byte[] file) {
    if (file.length > FILE_MAX_SIZE_AS_MB * 1024 * 1024) { // 2MB in bytes
      throw new BadRequestException("FIle size must be less than " + FILE_MAX_SIZE_AS_MB + "MB");
    }
    byteArrayTypeValidator.accept(file, "image");
  }
}
