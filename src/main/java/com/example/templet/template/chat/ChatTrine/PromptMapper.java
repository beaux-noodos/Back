package com.example.templet.template.chat.ChatTrine;

import com.example.templet.endpoint.rest.model.Prompt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PromptMapper {
  public com.example.templet.template.chat.ChatTrine.Prompt toDomain(Prompt rest, Chat chat) {
    return com.example.templet.template.chat.ChatTrine.Prompt.builder()
        .id(rest.getId())
        .updatedAt(rest.getUpdatedAt())
        .creationDatetime(rest.getCreationDatetime())
        .body(rest.getBody())
        .promptCategory(rest.getPromptCategory())
        .chat(chat)
        .build();
  }

  public Prompt toRest(com.example.templet.template.chat.ChatTrine.Prompt domain) {
    return new Prompt()
        .id(domain.getId())
        .updatedAt(domain.getUpdatedAt())
        .creationDatetime(domain.getCreationDatetime())
        .body(domain.getBody())
        .promptCategory(domain.getPromptCategory());
  }
}
