package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.rest.model.Prompt;
import com.example.templet.service.MessageChatFormatService;
import com.example.templet.template.chat.ChatTrine.Chat;
import com.example.templet.template.chat.ChatTrine.ChatRepository;
import com.example.templet.template.chat.ChatTrine.PromptMapper;
import com.example.templet.template.chat.ChatTrine.PromptService;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class ChatGPTController {

  private final MessageChatFormatService messageChatFormatService;
  private final PromptService promptService;
  private final PromptMapper promptMapper;
  private final ChatRepository chatRepository;

  @GetMapping("/users/{id}/chat")
  public String chat(
      @PathVariable String id, @RequestParam(value = "prompt", required = false) String prompt)
      throws IOException {
    return messageChatFormatService.userChat(id, prompt);
  }

  // TODO: chat NEW /users/:id/technical-solution/:tsid/chat
  @GetMapping("/users/{id}/technicalSolution/{tsid}/chat")
  public String chat(
      @PathVariable String id,
      @PathVariable String tsid,
      @RequestParam(value = "prompt", required = false) String prompt)
      throws IOException {
    return messageChatFormatService.userChat(id, prompt);
  }

  @GetMapping("/technicalSolution/{id}/prompts")
  public List<Prompt> createPrompt(@PathVariable("id") String userId) {
    return promptService.getAllPromptsByUser(userId).stream().map(promptMapper::toRest).toList();
  }

  @PutMapping("/technicalSolution/{id}/chat")
  public com.example.templet.endpoint.rest.model.Prompt updatePrompt(
      @PathVariable("id") String userId,
      @RequestBody com.example.templet.endpoint.rest.model.Prompt prompt) {
    Chat chat = chatRepository.findById(userId).orElse(null);
    return promptMapper.toRest(
        promptService.createOrUpdatePrompt(promptMapper.toDomain(prompt, chat)));
  }
}
