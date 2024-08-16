package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.mapper.ProjectSessionMapper;
import com.example.templet.endpoint.mapper.ReactionMapper;
import com.example.templet.endpoint.rest.model.ProjectSession;
import com.example.templet.endpoint.rest.model.Reaction;
import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.Whoami;
import com.example.templet.repository.model.ProjectSessionReaction;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.ProjectService;
import com.example.templet.service.ProjectSessionReactionService;
import com.example.templet.service.ProjectSessionService;
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
public class ProjectSessionController {
  private final UserAuthService userAuthService;
  private final JwtUtils jwtUtils;
  private final ProjectService projectService;
  private final ProjectSessionService projectSessionService;
  private final ProjectSessionReactionService projectSessionReactionService;
  private final ProjectSessionMapper mapper;
  private final ReactionMapper reactionMapper;

  @GetMapping(value = "/projects/*/project-sessions")
  public List<ProjectSession> getProjectSessions(
      @RequestParam(required = false, value = "is_suggest") Boolean isSuggest,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      HttpServletRequest request) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    if (isSuggest != null && isSuggest) {
      String token = jwtUtils.parseJwt(request.getHeader("Authorization"));
      Whoami whoami = userAuthService.whoami(token);
      return projectSessionService.findAll().stream().map(mapper::toRest).toList();
    }
    return projectSessionService.findAll(pageFromOne, boundedPageSize).stream()
        .map(mapper::toRest)
        .toList();
  }

  @GetMapping(value = "/users/{uid}/project-sessions")
  public List<ProjectSession> getUsersProjectSessions(
      @PathVariable(name = "uid") String userId,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      HttpServletRequest request) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    return projectSessionService
        .getUsersProjectSessions(userId, pageFromOne, boundedPageSize)
        .stream()
        .map(mapper::toRest)
        .toList();
  }

  @GetMapping(value = "/projects/*/project-sessions/{id}")
  public ProjectSession getProjectSessionById(@PathVariable String id) {
    return mapper.toRest(projectSessionService.findById(id));
  }

  @PutMapping(value = "/projects/*/project-sessions/{id}")
  public ProjectSession crupdateProjectSession(
      @PathVariable(name = "id") String id, @RequestBody ProjectSession toUpdate) {
    return mapper.toRest(
        projectSessionService.crupdateProjectSession(mapper.toDomain(toUpdate), id));
  }

  @DeleteMapping(value = "/projects/*/project-sessions/{id}")
  public ProjectSession deleteProjectSession(
      @PathVariable(name = "id") String id, @RequestBody ProjectSession toUpdate) {
    return null;
  }

  @PutMapping(value = "users/{uid}/projects/*/project-sessions/{xid}/react")
  public Reaction crupdateReactProjectSession(
      @PathVariable(name = "uid") String uid,
      @PathVariable(name = "xid") String xid,
      @RequestBody Reaction reaction) {
    ProjectSessionReaction projectSessionReaction =
        reactionMapper.toDomainProjectSessionReaction(reaction);
    projectSessionReaction.setSubject(projectSessionService.findById(reaction.getSubjectId()));
    return reactionMapper.toRestReaction(
        projectSessionReactionService.react(uid, xid, projectSessionReaction));
  }

  @GetMapping(value = "/projects/*projectSessions/{xid}/react")
  public List<Reaction> getReactProjectSession(
      @PathVariable(name = "xid") String xid,
      @RequestParam(required = false, value = "uid") String uid,
      @RequestParam(required = false, value = "have_like_reaction") Boolean havelikeReaction,
      @RequestParam(required = false, value = "have_vision") Boolean haveVision,
      @RequestParam(required = false, value = "have_stars_number") Boolean haveStarsNumber,
      @RequestParam(required = false, value = "have_comment") Boolean haveComment,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    if (uid == null
        && haveStarsNumber == null
        && haveComment == null
        && havelikeReaction == null
        && haveVision == null) {
      List<com.example.templet.template.sucgestIAWithReaction.Reaction> reactions =
          projectSessionReactionService.findAllBySubjectId(xid, pageFromOne, boundedPageSize);
      return reactions.stream().map(reactionMapper::toRestReaction).toList();
    }
    List<com.example.templet.template.sucgestIAWithReaction.Reaction> reactions =
        projectSessionReactionService.findAll();
    return reactions.stream().map(reactionMapper::toRestReaction).toList();
  }
}
