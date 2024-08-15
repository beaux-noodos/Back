package com.example.templet.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private int jwtExpiration;

  public String getJwtSecret() {
    return jwtSecret;
  }

  public int getJwtExpiration() {
    return jwtExpiration;
  }

  public static final String TOKEN_PREFIX = "Bearer ";
}
