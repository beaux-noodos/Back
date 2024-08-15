package com.example.templet.service;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.model.validator.EntityModelValidator;
import com.example.templet.repository.CourseSessionRepository;
import com.example.templet.repository.dao.CourseSessionDao;
import com.example.templet.repository.model.CourseSession;
import com.example.templet.template.sucgestIAWithReaction.HaveReaction;
import com.example.templet.template.sucgestIAWithReaction.HaveReactionService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CourseSessionService extends HaveReactionService {
  private final CourseSessionRepository repository;
  private final CourseSessionDao courseSessionDao;
  private EntityModelValidator entityModelValidator;

  public List<CourseSession> findAll() {
    return repository.findAll();
  }

  public List<CourseSession> findAll(PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllWithPage(pageable);
  }

  @Transactional
  public CourseSession save(CourseSession toSave) {
    entityModelValidator.accept(toSave);
    return repository.save(toSave);
  }

  @Override
  public CourseSession save(HaveReaction toSave) {
    CourseSession courseSession = (CourseSession) toSave;
    entityModelValidator.accept(toSave);
    return repository.save(courseSession);
  }

  @Transactional
  public CourseSession crupdateCourseSession(CourseSession courseSession, String courseSessionId) {
    entityModelValidator.accept(courseSession);
    Optional<CourseSession> courseSessionOptional = repository.findById(courseSessionId);
    if (courseSessionOptional.isPresent()) {
      CourseSession courseSessionFromDomain = courseSessionOptional.get();
      if (courseSession.getLocation() == null) {
        courseSession.setLocation(courseSessionFromDomain.getLocation());
      }
      if (courseSession.getCourse() == null) {
        courseSession.setCourse(courseSessionFromDomain.getCourse());
      }
      courseSession.setCreationDatetime(courseSessionFromDomain.getCreationDatetime());
    }
    return repository.save(courseSession);
  }

  @Override
  public CourseSession findById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("CourseSession with id " + id + " not found"));
  }

  public Boolean checkSelfCourseSession(
      String courseSessionId, String courseSessionIdFromEndpoint) {
    Optional<CourseSession> courseSessionOptional =
        repository.findById(courseSessionIdFromEndpoint);
    if (courseSessionOptional.isEmpty()) {
      return true;
    }
    CourseSession courseSession = courseSessionOptional.get();
    return courseSession.getId().equals(courseSessionId);
  }

  public List<CourseSession> findAllByCourse(
      String courseId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());

    return repository.findAllByCourseId(courseId, pageable);
  }

  public List<CourseSession> findAllByLocation(
      String locationId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllByLocationId(locationId, pageable);
  }

  public List<CourseSession> getUsersCourseSessions(
      String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.getUsersCourseSessions(userId, pageable);
  }
}
