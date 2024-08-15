package com.example.templet.template.file.validator.utils;

public interface FileExtensionValidator {
  boolean isValidFile(String filename);

  void accept(String filename);
}
