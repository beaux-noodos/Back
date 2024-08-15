package com.example.templet.endpoint.mapper;

import com.example.templet.endpoint.rest.model.Whoami;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class WhoamiMapper {
  private final UserMapper userMapper;

  public Whoami toRest(com.example.templet.model.Whoami model) {
    return new Whoami().bearer(model.getBearer()).user(userMapper.toRest(model.getUser()));
  }

  public com.example.templet.model.Whoami toDomain(Whoami model) {
    return com.example.templet.model.Whoami.builder()
        .bearer(model.getBearer())
        .user(userMapper.toDomain((model.getUser())))
        .build();
  }
}
