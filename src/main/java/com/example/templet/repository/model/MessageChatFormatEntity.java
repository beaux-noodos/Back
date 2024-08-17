package com.example.templet.repository.model;

import static com.example.templet.service.utils.FormatString.escapeJsonString;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.example.templet.template.chat.ChatTrine.Chat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageChatFormatEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  String id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  User user;

  @CreationTimestamp private Instant creationDatetime = Instant.now();

  @ManyToOne
  @JoinColumn(name = "chat_id")
  @JsonIgnore
  Chat chat;

  String clientMessage;
  String AssistantMessage;

  public String toString(int stringSpaceNumber) {
    String space = "";
    for (int i = 0; i < stringSpaceNumber; i++) {
      space += " ";
    }
    return space
        + "{"
        + "\n"
        + space
        + "  "
        + "\"role\": \"user\","
        + "\n"
        + space
        + "  "
        + "\"content\":  \"(to "
        + creationDatetime.toString()
        + ")"
        + escapeJsonString(clientMessage)
        + "\""
        + "\n"
        + space
        + "},"
        + "\n"
        + space
        + "{"
        + "\n"
        + space
        + "  "
        + "\"role\": \"assistant\","
        + "\n"
        + space
        + "  "
        + "\"content\": \""
        + escapeJsonString(AssistantMessage)
        + "\""
        + "\n"
        + space
        + "},";
  }

  public static String toString(
      List<MessageChatFormatEntity> messageChatFormatEntities, int stringSpaceNumber) {
    String value = "";
    for (MessageChatFormatEntity messageChatFormatEntity : messageChatFormatEntities) {
      value = messageChatFormatEntity.toString(stringSpaceNumber) + "\n" + value;
    }
    return value;
  }
}
