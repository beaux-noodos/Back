package com.example.templet.template.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GlobalThemStructure {
  private ChatTheme chatTheme;
  Class<? extends IsInChat> aClass;
  IsInChat isInChat;
  IsInChatService isInChatService;
}
