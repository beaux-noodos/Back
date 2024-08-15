package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.mapper.UserMapper;
import com.example.templet.endpoint.mapper.WhoamiMapper;
import com.example.templet.endpoint.rest.model.AuthenticationPayload;
import com.example.templet.endpoint.rest.model.SignUp;
import com.example.templet.endpoint.rest.model.Whoami;
import com.example.templet.model.exception.BadRequestException;
import com.example.templet.repository.model.User;
import com.example.templet.repository.model.UserAuth;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class AuthController {
  private WhoamiMapper whoamiMapper;
  private UserMapper userMapper;
  private UserAuthService userAuthService;
  private JwtUtils jwtUtils;

  @GetMapping("/whoami")
  public Whoami whoami(HttpServletRequest request) {
    String token = jwtUtils.parseJwt(request.getHeader("Authorization"));
    com.example.templet.model.Whoami whoami = userAuthService.whoami(token);
    return whoamiMapper.toRest(whoami);
  }

  @PostMapping("/signin")
  public Whoami login(@RequestBody AuthenticationPayload authenticationPayload) {
    String password = authenticationPayload.getPassword();
    String email = authenticationPayload.getEmail();
    String jwt = userAuthService.GetToken(password, email);
    if (jwt == null) {
      throw new BadRequestException("Password or email is wrong");
    }
    UserAuth userAuth = userAuthService.GetByToken(jwt);

    return new Whoami().bearer(jwt).user(userMapper.toRest(userAuth.getUser()));
  }

  @PostMapping("/signup")
  public Whoami register(@RequestBody SignUp signUp) {
    User savedUser = UserMapper.toDomain(signUp);
    UserAuth savedUserAuth =
        UserAuth.builder()
            .id(signUp.getId())
            .user(savedUser)
            .password(signUp.getPassword())
            .build();
    return whoamiMapper.toRest(userAuthService.save(savedUser, savedUserAuth));
  }
}
