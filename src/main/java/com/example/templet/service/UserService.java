package com.example.templet.service;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.enums.ActionEnum;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.model.validator.UserValidator;
import com.example.templet.repository.CourseRepository;
import com.example.templet.repository.UserRepository;
import com.example.templet.repository.model.Course;
import com.example.templet.repository.model.User;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository repository;
  private final CourseRepository courseRepository;
  private UserValidator userValidator;

  public List<User> findAllByName(String name, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.getUserByName(name, pageable);
  }

  @Transactional
  public User save(User toSave) {
    userValidator.accept(toSave);
    return repository.save(toSave);
  }

  @Transactional
  public User crupdateUser(User user, String userId) {
    userValidator.accept(user);
    Optional<User> userOptional = repository.findById(userId);
    if (userOptional.isPresent()) {
      User userFromDomain = userOptional.get();
      user.setCreationDatetime(userFromDomain.getCreationDatetime());
      user.setRole(userFromDomain.getRole());
      user.setPhotoKey(userFromDomain.getPhotoKey());
    }
    return repository.save(user);
  }

  @Transactional
  public User updateUserPhotoKey(User user) {
    userValidator.accept(user);
    Optional<User> userOptional = repository.findById(user.getId());
    if (userOptional.isPresent()) {
      User userFromDomain = userOptional.get();
      user.setCreationDatetime(userFromDomain.getCreationDatetime());
      user.setRole(userFromDomain.getRole());
    }
    return repository.save(user);
  }

  @Transactional
  public User updateUserBannerKey(User user) {
    userValidator.accept(user);
    Optional<User> userOptional = repository.findById(user.getId());
    if (userOptional.isPresent()) {
      User userFromDomain = userOptional.get();
      user.setCreationDatetime(userFromDomain.getCreationDatetime());
      user.setRole(userFromDomain.getRole());
      user.setPhotoKey(userFromDomain.getPhotoKey());
    }
    return repository.save(user);
  }

  public User findById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
  }

  public Boolean checkSelfUser(String userId, String userIdFromEndpoint) {
    Optional<User> userOptional = repository.findById(userIdFromEndpoint);
    if (userOptional.isEmpty()) {
      return true;
    }
    User user = userOptional.get();
    return user.getId().equals(userId);
  }

  public Course userFolowAction(String userId, String courseId, ActionEnum actionEnum) {
    Optional<Course> courseOptional = courseRepository.findById(courseId);
    if (courseOptional.isEmpty()) {
      throw new NotFoundException("Courses with id " + courseId + " not found");
    }
    Optional<User> userOptional = repository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new NotFoundException("User with id " + userId + " not found");
    }
    Course course = courseOptional.get();
    User user = userOptional.get();
    List<User> coursesFolow = course.getInterestedUsers();
    List<User> coursesInterest = course.getFollowers();
    if (coursesInterest.contains(user)) {
      if (actionEnum.equals(ActionEnum.FOLLOW) || actionEnum.equals(ActionEnum.UNINTERESTED)) {
        coursesInterest.remove(user);
      }
    } else {
      if (actionEnum.equals(ActionEnum.INTERESTED)) {
        coursesInterest.add(user);
      }
    }
    if (coursesFolow.contains(user)) {
      if (actionEnum.equals(ActionEnum.INTERESTED) || actionEnum.equals(ActionEnum.UNFOLLOW)) {
        coursesFolow.remove(user);
      }
    } else {
      if (actionEnum.equals(ActionEnum.FOLLOW)) {
        coursesFolow.add(user);
      }
    }
    course.setFollowers(coursesFolow);
    course.setInterestedUsers(coursesInterest);
    return courseRepository.save(course);
  }
}
