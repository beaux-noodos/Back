package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.mapper.CourseSessionMapper;
import com.example.templet.endpoint.mapper.ReactionMapper;
import com.example.templet.endpoint.rest.model.CourseSession;
import com.example.templet.endpoint.rest.model.Reaction;
import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.Whoami;
import com.example.templet.repository.model.CourseSessionReaction;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.CourseService;
import com.example.templet.service.CourseSessionReactionService;
import com.example.templet.service.CourseSessionService;
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
public class CourseSessionController {
  private final UserAuthService userAuthService;
  private final JwtUtils jwtUtils;
  private final CourseService courseService;
  private final CourseSessionService courseSessionService;
  private final CourseSessionReactionService courseSessionReactionService;
  private final CourseSessionMapper mapper;
  private final ReactionMapper reactionMapper;

  @GetMapping(value = "/courses/*/course-sessions")
  public List<CourseSession> getCourseSessions(
      @RequestParam(required = false, value = "is_suggest") Boolean isSuggest,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      HttpServletRequest request) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    if (isSuggest != null && isSuggest) {
      String token = jwtUtils.parseJwt(request.getHeader("Authorization"));
      Whoami whoami = userAuthService.whoami(token);
      return courseSessionService.findAll().stream().map(mapper::toRest).toList();
    }
    return courseSessionService.findAll(pageFromOne, boundedPageSize).stream()
        .map(mapper::toRest)
        .toList();
  }

  @GetMapping(value = "/users/{uid}/course-sessions")
  public List<CourseSession> getUsersCourseSessions(
      @PathVariable(name = "uid") String userId,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      HttpServletRequest request) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    return courseSessionService
        .getUsersCourseSessions(userId, pageFromOne, boundedPageSize)
        .stream()
        .map(mapper::toRest)
        .toList();
  }

  @GetMapping(value = "/courses/*/course-sessions/{id}")
  public CourseSession getCourseSessionById(@PathVariable String id) {
    return mapper.toRest(courseSessionService.findById(id));
  }

  @PutMapping(value = "/courses/*/course-sessions/{id}")
  public CourseSession crupdateCourseSession(
      @PathVariable(name = "id") String id, @RequestBody CourseSession toUpdate) {
    return mapper.toRest(courseSessionService.crupdateCourseSession(mapper.toDomain(toUpdate), id));
  }

  @DeleteMapping(value = "/courses/*/course-sessions/{id}")
  public CourseSession deleteCourseSession(
      @PathVariable(name = "id") String id, @RequestBody CourseSession toUpdate) {
    return null;
  }

  @PutMapping(value = "users/{uid}/courses/*/course-sessions/{xid}/react")
  public Reaction crupdateReactCourseSession(
      @PathVariable(name = "uid") String uid,
      @PathVariable(name = "xid") String xid,
      @RequestBody Reaction reaction) {
    CourseSessionReaction courseSessionReaction =
        reactionMapper.toDomainCourseSessionReaction(reaction);
    courseSessionReaction.setSubject(courseSessionService.findById(reaction.getSubjectId()));
    return reactionMapper.toRestReaction(
        courseSessionReactionService.react(uid, xid, courseSessionReaction));
  }

  @GetMapping(value = "/courses/*courseSessions/{xid}/react")
  public List<Reaction> getReactCourseSession(
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
          courseSessionReactionService.findAllBySubjectId(xid, pageFromOne, boundedPageSize);
      return reactions.stream().map(reactionMapper::toRestReaction).toList();
    }
    List<com.example.templet.template.sucgestIAWithReaction.Reaction> reactions =
        courseSessionReactionService.findAll();
    return reactions.stream().map(reactionMapper::toRestReaction).toList();
  }
}
