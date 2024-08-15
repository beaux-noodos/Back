package com.example.templet.service.utils;

public class FormatString {
  public static String escapeJsonString(String input) {
    if (input == null) {
      return null;
    }
    return input
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\b", "\\b")
        .replace("\f", "\\f")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t");
  }
}
