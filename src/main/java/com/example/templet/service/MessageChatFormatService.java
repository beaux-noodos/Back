package com.example.templet.service;

import static com.example.templet.service.utils.FormatString.escapeJsonString;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.validator.EntityModelValidator;
import com.example.templet.repository.MessageChatFormatRepository;
import com.example.templet.repository.ProjectReactionRepository;
import com.example.templet.repository.ProjectRepository;
import com.example.templet.repository.model.MessageChatFormatEntity;
import com.example.templet.repository.model.Project;
import com.example.templet.repository.model.User;
import com.example.templet.template.chat.ChatService;
import com.example.templet.template.chat.ChatTheme;
import com.example.templet.template.chat.ChatUtils;
import com.example.templet.template.chat.GlobalThemStructure;
import com.example.templet.template.chat.MessageChatFormat;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MessageChatFormatService {
  private final ProjectRepository repository;
  private final ProjectReactionRepository xxxxxReactionRepository;
  private EntityModelValidator entityModelValidator;

  private final UserService userService;
  private final MessageChatFormatRepository messageChatFormatRepository;
  private final ChatService chatService;

  public List<MessageChatFormatEntity> findAll() {
    return messageChatFormatRepository.findAll();
  }

  public List<MessageChatFormatEntity> findByUserId(
      String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return messageChatFormatRepository.findByUserIdOrderByCreationDatetimeDesc(userId, pageable);
  }

  public Optional<MessageChatFormatEntity> findById(String id) {
    return messageChatFormatRepository.findById(id);
  }

  public MessageChatFormatEntity save(MessageChatFormatEntity message) {
    return messageChatFormatRepository.save(message);
  }

  public void deleteById(String id) {
    messageChatFormatRepository.deleteById(id);
  }

  public MessageChatFormat mappedToModel(MessageChatFormatEntity messageChatFormatEntity) {
    return MessageChatFormat.builder()
        .clientMessage(
            "(le "
                + messageChatFormatEntity.getCreationDatetime().toString()
                + (") ")
                + messageChatFormatEntity.getClientMessage())
        .AssistantMessage(messageChatFormatEntity.getAssistantMessage())
        .build();
  }

  public MessageChatFormatEntity mappedToEntity(MessageChatFormat messageChatFormat, User user) {
    return MessageChatFormatEntity.builder()
        .user(user)
        .creationDatetime(Instant.now())
        .clientMessage(messageChatFormat.getClientMessage())
        .AssistantMessage(messageChatFormat.getAssistantMessage())
        .build();
  }

  @Transactional
  public String userChat(String userId, String clientMessage) throws IOException {
    Pageable pageable = PageRequest.of(0, 10);
    User user = userService.findById(userId);
    List<MessageChatFormatEntity> messageChatFormatEntities =
        messageChatFormatRepository.findByUserIdOrderByCreationDatetimeDesc(userId, pageable);

    String assistantThemeMessage =
        chatService.chat(
            escapeJsonString(clientMessage),
            "",
            escapeJsonString(ChatUtils.getPropsForTakeTheme()));

    String apropriateSystemMessage =
        ChatUtils.generateApropriatePropsTheme(
            (assistantThemeMessage),
            List.of(
                GlobalThemStructure.builder()
                    .isInChat(new Project())
                    .isInChatService(
                        new ProjectService(
                            repository, xxxxxReactionRepository, entityModelValidator))
                    .chatTheme(ChatTheme.PROJECT)
                    .aClass(Project.class)
                    .build()));

    String assistantMessage =
        chatService.chat(
            escapeJsonString(clientMessage),
            MessageChatFormatEntity.toString(messageChatFormatEntities, 6),
            escapeJsonString(
                ChatUtils.generateAapliationdefinition()
                    + apropriateSystemMessage
                    + ChatUtils.askChat()));

    MessageChatFormat newMessageChatFormat =
        MessageChatFormat.builder()
            .clientMessage(escapeJsonString(clientMessage))
            .AssistantMessage(escapeJsonString(assistantMessage))
            .build();
    MessageChatFormatEntity messageChatFormatEntity = mappedToEntity(newMessageChatFormat, user);
    messageChatFormatRepository.save(messageChatFormatEntity);
    return assistantMessage;
  }
}
