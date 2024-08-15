package com.example.templet.config;

import com.example.templet.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final UserAuthRepository userAuthRepository;

  @Bean
  public UserDetailsService userDetailsService() {
    return subject ->
        userAuthRepository
            .findByUserMail(subject)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
