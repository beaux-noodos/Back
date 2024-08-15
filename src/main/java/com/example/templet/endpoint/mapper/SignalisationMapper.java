package com.example.templet.endpoint.mapper;

import com.example.templet.endpoint.rest.model.Signalisation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SignalisationMapper {
  private final UserMapper userMapper;

  public com.example.templet.repository.model.Signalisation toDomain(Signalisation rest) {
    return com.example.templet.repository.model.Signalisation.builder()
        .id(rest.getId())
        .lastUpdateDatetime(rest.getUpdatedAt())
        .creationDatetime(rest.getCreationDatetime())
        .user(userMapper.toDomain((rest.getUser())))
        .build();
  }

  public Signalisation toRest(com.example.templet.repository.model.Signalisation domain) {
    return new Signalisation()
        .id(domain.getId())
        .updatedAt(domain.getLastUpdateDatetime())
        .creationDatetime(domain.getCreationDatetime())
        .user(userMapper.toRest(domain.getUser()));
  }
}
