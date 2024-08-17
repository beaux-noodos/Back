package com.example.templet.template.chat.ChatTrine;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PromptService {
  private final ChatRepository chatRepository;
  private final PromptRepository promptRepository;

  public List<Prompt> getAllPrompts() {
    return promptRepository.findAll();
  }

  public List<Prompt> getAllPromptsByUser(String userId) {
    Chat chat = chatRepository.findByUserId(userId);
    return chat.getPrompts();
  }

  public Optional<Prompt> getPromptById(String id) {
    return promptRepository.findById(id);
  }

  public Prompt createOrUpdatePrompt(Prompt prompt) {
    return promptRepository.save(prompt);
  }
}
