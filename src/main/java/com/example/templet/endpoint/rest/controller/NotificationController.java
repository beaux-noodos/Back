package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.mapper.NotificationMapper;
import com.example.templet.endpoint.mapper.ReactionMapper;
import com.example.templet.endpoint.rest.model.Notification;
import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.Whoami;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.NotificationReactionService;
import com.example.templet.service.NotificationService;
import com.example.templet.service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class NotificationController {
  private final UserAuthService userAuthService;
  private final JwtUtils jwtUtils;
  private final NotificationService notificationService;
  private final NotificationReactionService notificationReactionService;
  private final NotificationMapper mapper;
  private final ReactionMapper reactionMapper;

  @GetMapping(value = "/notifications")
  public List<Notification> getNotifications(
      @RequestParam(required = false, value = "is_suggest") Boolean isSuggest,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      HttpServletRequest request) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    if (isSuggest != null && isSuggest) {
      String token = jwtUtils.parseJwt(request.getHeader("Authorization"));
      Whoami whoami = userAuthService.whoami(token);
      return notificationService
          .findSuggestAi(whoami.getUser().getId(), pageFromOne, boundedPageSize)
          .stream()
          .map(mapper::toRest)
          .toList();
    }
    return notificationService.findAll(pageFromOne, boundedPageSize).stream()
        .map(mapper::toRest)
        .toList();
  }

  @GetMapping(value = "/notifications/{id}")
  public Notification getNotificationById(@PathVariable String id) {
    return mapper.toRest(notificationService.findById(id));
  }

  @PutMapping(value = "/notifications/{id}")
  public Notification crupdatnotificatione(
      @PathVariable(name = "id") String id, @RequestBody Notification toUpdate) {
    return mapper.toRest(notificationService.crupdatnotificatione(mapper.toDomain(toUpdate), id));
  }

  @DeleteMapping(value = "/notifications/{id}")
  public Notification deletnotificatione(
      @PathVariable(name = "id") String id, @RequestBody Notification toUpdate) {
    return null;
  }
}
