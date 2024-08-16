package com.example.templet.endpoint.rest.controller;

import com.example.templet.service.MessageChatFormatService;
import java.io.IOException;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class ChatGPTController {

  private final MessageChatFormatService messageChatFormatService;

  @GetMapping("/users/{id}/chat")
  public String chat(
      @PathVariable String id, @RequestParam(value = "prompt", required = false) String prompt)
      throws IOException {
    return messageChatFormatService.userChat(id, prompt);
  }
  //TODO: chat NEW
  @GetMapping("/users/{id}/technical-solution/{tsid}/chat")
  public String chat(
          @PathVariable String id,
          @PathVariable String tsid,
          @RequestParam(value = "prompt", required = false) String prompt)
          throws IOException {
    return messageChatFormatService.userChat(id, prompt);
  }
  //TODO: chat NEW
  @PutMapping("/technical-solution/{tsid}/chat")
  public String crupdatePromptChat(
          @PathVariable String tsid,
          @RequestParam(value = "prompt", required = false) String prompt)
          throws IOException {
    return messageChatFormatService.userChat(tsid, prompt);
  }
}
