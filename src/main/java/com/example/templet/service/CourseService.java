package com.example.templet.service;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.model.validator.EntityModelValidator;
import com.example.templet.repository.CourseReactionRepository;
import com.example.templet.repository.CourseRepository;
import com.example.templet.repository.model.Course;
import com.example.templet.service.utils.AIUtils;
import com.example.templet.template.chat.IsInChat;
import com.example.templet.template.chat.IsInChatService;
import com.example.templet.template.file.HavePicture;
import com.example.templet.template.file.HavePictureService;
import com.example.templet.template.sucgestIAWithReaction.HaveReaction;
import com.example.templet.template.sucgestIAWithReaction.HaveReactionService;
import com.example.templet.template.sucgestIAWithReaction.SymbolicAIService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CourseService extends HaveReactionService
    implements HavePictureService, IsInChatService {
  private final CourseRepository repository;
  private final CourseReactionRepository xxxxxReactionRepository;
  private EntityModelValidator entityModelValidator;

  public List<Course> findAll() {
    return repository.findAll();
  }

  public List<Course> findAll(PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllWithPage(pageable);
  }

  @Transactional
  public Course save(Course toSave) {
    entityModelValidator.accept(toSave);
    return repository.save(toSave);
  }

  @Override
  @Transactional
  public Course save(HaveReaction toSave) {
    Course course = (Course) toSave;
    entityModelValidator.accept(toSave);
    return repository.save(course);
  }

  @Override
  @Transactional
  public HavePicture save(HavePicture havePicture) {
    Course course = (Course) havePicture;
    entityModelValidator.accept(course);
    return repository.save(course);
  }

  @Transactional
  public Course crupdateCourse(Course course, String courseId) {
    entityModelValidator.accept(course);
    Optional<Course> courseOptional = repository.findById(courseId);
    if (courseOptional.isPresent()) {
      Course courseFromDomain = courseOptional.get();
      if (course.getUser() == null) {
        course.setUser(courseFromDomain.getUser());
      }
      course.setCreationDatetime(courseFromDomain.getCreationDatetime());
    }
    return repository.save(course);
  }

  @Override
  public Course findById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Course with id " + id + " not found"));
  }

  public Boolean checkSelfCourse(String courseId, String courseIdFromEndpoint) {
    Optional<Course> courseOptional = repository.findById(courseIdFromEndpoint);
    if (courseOptional.isEmpty()) {
      return true;
    }
    Course course = courseOptional.get();
    return course.getId().equals(courseId);
  }

  public List<Course> findAllByUserId(String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllByUserId(userId, pageable);
  }

  public List<Course> findSuggestAi(String userId, PageFromOne page, BoundedPageSize pageSize) {
    int pageValue = page.getValue();
    int pageSizeValue = pageSize.getValue();
    Pageable pageableXxx = PageRequest.of(0, 500);

    List<Course> courses = repository.findAllByUserId(userId, pageableXxx);

    Collections.sort(
        courses,
        new Comparator<Course>() {
          @Override
          public int compare(Course p1, Course p2) {
            long point1 =
                SymbolicAIService.generateUserPersonalPointInOneSubject(
                        new ArrayList<>(), new ArrayList<>())
                    * SymbolicAIService.getSubjectAIPoints(p1.getId(), xxxxxReactionRepository);
            long point2 =
                SymbolicAIService.generateUserPersonalPointInOneSubject(
                        new ArrayList<>(), new ArrayList<>())
                    * SymbolicAIService.getSubjectAIPoints(p2.getId(), xxxxxReactionRepository);

            return Integer.compare((int) (point1 / 1_000_000L), (int) (point2 / 1_000_000L));
          }
        });

    return AIUtils.getPaginatedList(courses, pageValue, pageSizeValue);
  }

  public List<Course> findAllCoursesInterestedByUserId(
      String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllCoursesInterestedByUserId(userId, pageable);
  }

  public List<Course> findAllCoursesFollowersByUserId(
      String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllCoursesFollowersByUserId(userId, pageable);
  }

  @Override
  public List<IsInChat> getIsInChatToConsider() {
    return repository.findAll().stream().map(course -> (IsInChat) course).toList();
  }
}
