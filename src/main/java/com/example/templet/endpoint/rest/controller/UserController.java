package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.mapper.UserMapper;
import com.example.templet.endpoint.rest.model.User;
import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.enums.Role;
import com.example.templet.model.exception.ForbiddenException;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.UserAuthService;
import com.example.templet.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class UserController {
  private final JwtUtils jwtUtils;
  private final UserService userService;
  private final UserMapper userMapper;
  private final UserAuthService userAuthService;

  @GetMapping(value = "/users")
  public List<User> getUsers(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      @RequestParam(value = "name", required = false, defaultValue = "") String name) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    return userService.findAllByName(name, pageFromOne, boundedPageSize).stream()
        .map(userMapper::toRest)
        .toList();
  }

  @GetMapping(value = "/users/{id}")
  public User getUserById(@PathVariable String id) {
    return userMapper.toRest(userService.findById(id));
  }

  @PutMapping(value = "/users/{id}")
  public User crupdateUser(
      @PathVariable(name = "id") String userId,
      @RequestBody User toUpdate,
      HttpServletRequest request) {
    String token = jwtUtils.parseJwt(request.getHeader("Authorization"));
    com.example.templet.model.Whoami whoami = userAuthService.whoami(token);
    if (whoami.getUser().getRole() == Role.INVESTOR
        || whoami.getUser().getRole() == Role.PROJECT_OWNER
        || whoami.getUser().getRole() == Role.TECHNICAL_SOLUTION) {
      if (toUpdate.getRole() == com.example.templet.endpoint.rest.model.Role.MANAGER) {
        throw new ForbiddenException("An CLIENT cannot update the MANAGER user");
      }
    }
    return userMapper.toRest(userService.crupdateUser(userMapper.toDomain(toUpdate), userId));
  }
}
