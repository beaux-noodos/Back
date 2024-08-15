package com.example.templet.service.utils;

public class ExceptionMessageBuilderUtils {
  public static String notFoundByIdMessageException(String resource, String id) {
    return "Resource of type " + resource + " identified by " + id + " not found";
  }
}
