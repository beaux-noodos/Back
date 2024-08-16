package com.example.templet.security;

import com.example.templet.model.exception.BadRequestException;
import com.example.templet.repository.UserAuthRepository;
import com.example.templet.repository.model.UserAuth;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@AllArgsConstructor
public class UserOfUserMatcher implements RequestMatcher {
  private final UserAuthRepository userAuthRepository;
  private final JwtUtils jwtUtils;
  private final HttpMethod method;
  private final String antPattern;
  private final List<String> roles;

  private String getSelfId(HttpServletRequest request, String stringBeforeId) {
    Pattern SELFABLE_URI_PATTERN = Pattern.compile(stringBeforeId + "/(?<id>[^/]+)(/.*)?");
    Matcher uriMatcher = SELFABLE_URI_PATTERN.matcher(request.getRequestURI());
    return uriMatcher.find() ? uriMatcher.group("id") : null;
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    AntPathRequestMatcher antMatcher = new AntPathRequestMatcher(antPattern, method.toString());
    if (request.getHeader("Authorization") == null) {
      return false;
    }
    String subject =
        jwtUtils.getSubjectFromJwtToken(request.getHeader("Authorization").split(" ")[1]);
    UserAuth user = userAuthRepository.findByUserMail(subject).orElseThrow(()->new BadRequestException("Invalid credentials"));
    String userIdFromRequest = user.getUser().getId();
    if (!antMatcher.matches(request)) {
      return false;
    }
    for (String role : roles) {
      if (user.getUser().getRole().name().equals(role)) {
        return true;
      }
    }
    return Objects.equals(userIdFromRequest, getSelfId(request, "users"));
  }

  @Override
  public MatchResult matcher(jakarta.servlet.http.HttpServletRequest request) {
    return RequestMatcher.super.matcher(request);
  }
}
