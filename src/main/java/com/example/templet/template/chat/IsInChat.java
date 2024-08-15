package com.example.templet.template.chat;

import java.util.List;

public interface IsInChat {
  public static String generateThemeData(List<IsInChat> isInChats) {
    StringBuilder value = new StringBuilder();
    for (IsInChat isInChat : isInChats) {
      value.append("\n").append(isInChat.getData());
    }
    return value.toString();
  }

  public String getData();

  public long getAssessmentPoint();
}
