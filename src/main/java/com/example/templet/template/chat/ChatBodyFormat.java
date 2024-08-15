package com.example.templet.template.chat;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class ChatBodyFormat {
  private String model = "gpt-4o";
  private List<MessageChatFormat> messages = new ArrayList<>();
}
