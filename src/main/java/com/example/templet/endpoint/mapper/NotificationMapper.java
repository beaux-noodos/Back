package com.example.templet.endpoint.mapper;

import com.example.templet.endpoint.rest.model.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class NotificationMapper {
  private final UserMapper userMapper;

  public com.example.templet.repository.model.Notification toDomain(Notification rest) {
    return com.example.templet.repository.model.Notification.builder()
        .id(rest.getId())
        .lastUpdateDatetime(rest.getUpdatedAt())
        .creationDatetime(rest.getCreationDatetime())
        .user(userMapper.toDomain((rest.getUser())))
        .build();
  }

  public Notification toRest(com.example.templet.repository.model.Notification domain) {
    return new Notification()
        .id(domain.getId())
        .updatedAt(domain.getLastUpdateDatetime())
        .creationDatetime(domain.getCreationDatetime())
        .user(userMapper.toRest(domain.getUser()));
  }
}
