package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.mapper.CourseMapper;
import com.example.templet.endpoint.mapper.CourseSessionMapper;
import com.example.templet.endpoint.mapper.ReactionMapper;
import com.example.templet.endpoint.rest.model.Course;
import com.example.templet.endpoint.rest.model.Reaction;
import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.Whoami;
import com.example.templet.repository.model.CourseReaction;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.CourseReactionService;
import com.example.templet.service.CourseService;
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
public class CourseController {
  private final UserAuthService userAuthService;
  private final JwtUtils jwtUtils;
  private final CourseService courseService;
  private final CourseReactionService courseReactionService;
  private final CourseMapper mapper;
  private final ReactionMapper reactionMapper;
  private final CourseSessionMapper courseSessionMapper;

  @GetMapping(value = "/courses")
  public List<Course> getCourses(
      @RequestParam(required = false, value = "is_suggest") Boolean isSuggest,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      HttpServletRequest request) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    if (isSuggest != null && isSuggest) {
      String token = jwtUtils.parseJwt(request.getHeader("Authorization"));
      Whoami whoami = userAuthService.whoami(token);
      return courseService
          .findSuggestAi(whoami.getUser().getId(), pageFromOne, boundedPageSize)
          .stream()
          .map(
              course ->
                  mapper.toRest(
                      course,
                      course.getSessions().stream().map(courseSessionMapper::toRest).toList()))
          .toList();
    }
    return courseService.findAll(pageFromOne, boundedPageSize).stream()
        .map(
            course ->
                mapper.toRest(
                    course,
                    course.getSessions().stream().map(courseSessionMapper::toRest).toList()))
        .toList();
  }

  @GetMapping(value = "/courses/{id}")
  public Course getCourseById(@PathVariable String id) {
    return mapper.toRest(
        courseService.findById(id),
        courseService.findById(id).getSessions().stream()
            .map(courseSessionMapper::toRest)
            .toList());
  }

  @PutMapping(value = "/courses/{id}")
  public Course crupdateCourse(@PathVariable(name = "id") String id, @RequestBody Course toUpdate) {
    com.example.templet.repository.model.Course course =
        courseService.crupdateCourse(
            mapper.toDomain(
                toUpdate,
                Objects.requireNonNull(toUpdate.getSessions()).stream()
                    .map(courseSessionMapper::toDomain)
                    .toList()),
            id);
    return mapper.toRest(
        course, course.getSessions().stream().map(courseSessionMapper::toRest).toList());
  }

  @DeleteMapping(value = "/courses/{id}")
  public Course deleteCourse(@PathVariable(name = "id") String id, @RequestBody Course toUpdate) {
    return null;
  }

  @PutMapping(value = "users/{uid}/courses/{xid}/react")
  public Reaction crupdateReactCourse(
      @PathVariable(name = "uid") String uid,
      @PathVariable(name = "xid") String xid,
      @RequestBody Reaction reaction) {
    CourseReaction courseReaction = reactionMapper.toDomainCourseReaction(reaction);
    courseReaction.setSubject(courseService.findById(reaction.getSubjectId()));
    return reactionMapper.toRestReaction(courseReactionService.react(uid, xid, courseReaction));
  }

  @GetMapping(value = "courses/{xid}/react")
  public List<Reaction> getReactCourse(
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
          courseReactionService.findAllBySubjectId(xid, pageFromOne, boundedPageSize);
      return reactions.stream().map(reactionMapper::toRestReaction).toList();
    }
    List<com.example.templet.template.sucgestIAWithReaction.Reaction> reactions =
        courseReactionService.getReactionsByCriteria(
            xid,
            uid,
            havelikeReaction,
            haveVision,
            haveStarsNumber,
            haveComment,
            pageFromOne,
            boundedPageSize,
            CourseReaction.class);
    return reactions.stream().map(reactionMapper::toRestReaction).toList();
  }
}
