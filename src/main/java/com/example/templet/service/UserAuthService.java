package com.example.templet.service;

import com.example.templet.model.Whoami;
import com.example.templet.repository.UserAuthRepository;
import com.example.templet.repository.model.User;
import com.example.templet.repository.model.UserAuth;
import com.example.templet.security.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserAuthService {
  private final UserAuthRepository userAuthRepository;
  private final JwtUtils jwtUtils;
  private AuthenticationManager authenticationManager;
  private final UserService userService;
  private PasswordEncoder passwordEncoder;

  public void save(UserAuth toSave) {
    userAuthRepository.save(toSave);
  }

  @Transactional
  public Whoami save(User user, UserAuth userAuth) {
    User savedUser = userService.save(user);
    String password = userAuth.getPassword();
    userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));

    this.save(userAuth);
    String jwt = GetToken(password, userAuth.getUsername());

    return Whoami.builder().bearer(jwt).user(savedUser).build();
  }

  public UserAuth findByUserMail(String email) {
    return userAuthRepository
        .findByUserMail(email)
        .orElseThrow(() -> new UsernameNotFoundException(email));
  }

  public UserAuth GetByToken(String token) {
    String subject = jwtUtils.getSubjectFromJwtToken(token);
    return userAuthRepository
        .findByUserMail(subject)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public String GetToken(String password, String email) {

    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(email, password);

    Authentication authentication =
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserAuth userAuth = (UserAuth) authentication.getPrincipal();
    return jwtUtils.generateJwtToken(userAuth);
  }

  public Whoami whoami(String token) {
    String subject = jwtUtils.getSubjectFromJwtToken(token);

    UserAuth userAuth =
        userAuthRepository
            .findByUserMail(subject)
            .orElseThrow(() -> new UsernameNotFoundException(subject));
    return Whoami.builder().user(userAuth.getUser()).bearer(token).build();
  }
}
