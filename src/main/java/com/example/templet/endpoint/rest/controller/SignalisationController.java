package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.mapper.ReactionMapper;
import com.example.templet.endpoint.mapper.SignalisationMapper;
import com.example.templet.endpoint.rest.model.Signalisation;
import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.Whoami;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.SignalisationReactionService;
import com.example.templet.service.SignalisationService;
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
public class SignalisationController {
  private final UserAuthService userAuthService;
  private final JwtUtils jwtUtils;
  private final SignalisationService signalisationService;
  private final SignalisationReactionService signalisationReactionService;
  private final SignalisationMapper mapper;
  private final ReactionMapper reactionMapper;

  @GetMapping(value = "/signalisations")
  public List<Signalisation> getSignalisations(
      @RequestParam(required = false, value = "is_suggest") Boolean isSuggest,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      HttpServletRequest request) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    if (isSuggest != null && isSuggest) {
      String token = jwtUtils.parseJwt(request.getHeader("Authorization"));
      Whoami whoami = userAuthService.whoami(token);
      return signalisationService
          .findSuggestAi(whoami.getUser().getId(), pageFromOne, boundedPageSize)
          .stream()
          .map(mapper::toRest)
          .toList();
    }
    return signalisationService.findAll(pageFromOne, boundedPageSize).stream()
        .map(mapper::toRest)
        .toList();
  }

  @GetMapping(value = "/signalisations/{id}")
  public Signalisation getSignalisationById(@PathVariable String id) {
    return mapper.toRest(signalisationService.findById(id));
  }

  @PutMapping(value = "/signalisations/{id}")
  public Signalisation crupdateSignalisation(
      @PathVariable(name = "id") String id, @RequestBody Signalisation toUpdate) {
    return mapper.toRest(signalisationService.crupdateSignalisation(mapper.toDomain(toUpdate), id));
  }

  @DeleteMapping(value = "/signalisations/{id}")
  public Signalisation deleteSignalisation(
      @PathVariable(name = "id") String id, @RequestBody Signalisation toUpdate) {
    return null;
  }
}
