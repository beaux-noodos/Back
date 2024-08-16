package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.mapper.ProjectMapper;
import com.example.templet.endpoint.mapper.ProjectSessionMapper;
import com.example.templet.endpoint.mapper.ReactionMapper;
import com.example.templet.endpoint.rest.model.Project;
import com.example.templet.endpoint.rest.model.Reaction;
import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.Whoami;
import com.example.templet.repository.model.ProjectReaction;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.ProjectReactionService;
import com.example.templet.service.ProjectService;
import com.example.templet.service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
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
public class ProjectController {
  private final UserAuthService userAuthService;
  private final JwtUtils jwtUtils;
  private final ProjectService projectService;
  private final ProjectReactionService projectReactionService;
  private final ProjectMapper mapper;
  private final ReactionMapper reactionMapper;
  private final ProjectSessionMapper projectSessionMapper;

  @GetMapping(value = "/projects")
  public List<Project> getProjects(
      @RequestParam(required = false, value = "is_suggest") Boolean isSuggest,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      HttpServletRequest request) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    if (isSuggest != null && isSuggest) {
      String token = jwtUtils.parseJwt(request.getHeader("Authorization"));
      Whoami whoami = userAuthService.whoami(token);
      return projectService
          .findSuggestAi(whoami.getUser().getId(), pageFromOne, boundedPageSize)
          .stream()
          .map(
              project ->
                  mapper.toRest(
                      project,
                      project.getSessions().stream().map(projectSessionMapper::toRest).toList()))
          .toList();
    }
    return projectService.findAll(pageFromOne, boundedPageSize).stream()
        .map(
            project ->
                mapper.toRest(
                    project,
                    project.getSessions().stream().map(projectSessionMapper::toRest).toList()))
        .toList();
  }

  @GetMapping(value = "/projects/{id}")
  public Project getProjectById(@PathVariable String id) {
    return mapper.toRest(
        projectService.findById(id),
        projectService.findById(id).getSessions().stream()
            .map(projectSessionMapper::toRest)
            .toList());
  }

  @PutMapping(value = "/projects/{id}")
  public Project crupdateProject(
      @PathVariable(name = "id") String id, @RequestBody Project toUpdate) {
    com.example.templet.repository.model.Project project =
        projectService.crupdateProject(
            mapper.toDomain(
                toUpdate,
                Objects.requireNonNull(toUpdate.getSessions()).stream()
                    .map(projectSessionMapper::toDomain)
                    .toList()),
            id);
    return mapper.toRest(
        project, project.getSessions().stream().map(projectSessionMapper::toRest).toList());
  }

  @DeleteMapping(value = "/projects/{id}")
  public Project deleteProject(
      @PathVariable(name = "id") String id, @RequestBody Project toUpdate) {
    return null;
  }

  @PutMapping(value = "users/{uid}/projects/{xid}/react")
  public Reaction crupdateReactProject(
      @PathVariable(name = "uid") String uid,
      @PathVariable(name = "xid") String xid,
      @RequestBody Reaction reaction) {
    ProjectReaction projectReaction = reactionMapper.toDomainProjectReaction(reaction);
    projectReaction.setSubject(projectService.findById(reaction.getSubjectId()));
    return reactionMapper.toRestReaction(projectReactionService.react(uid, xid, projectReaction));
  }

  @GetMapping(value = "projects/{xid}/react")
  public List<Reaction> getReactProject(
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
          projectReactionService.findAllBySubjectId(xid, pageFromOne, boundedPageSize);
      return reactions.stream().map(reactionMapper::toRestReaction).toList();
    }
    List<com.example.templet.template.sucgestIAWithReaction.Reaction> reactions =
        projectReactionService.getReactionsByCriteria(
            xid,
            uid,
            havelikeReaction,
            haveVision,
            haveStarsNumber,
            haveComment,
            pageFromOne,
            boundedPageSize,
            ProjectReaction.class);
    return reactions.stream().map(reactionMapper::toRestReaction).toList();
  }
}
