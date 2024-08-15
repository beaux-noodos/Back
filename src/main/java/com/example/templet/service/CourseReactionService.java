package com.example.templet.service;

import com.example.templet.model.validator.ReactionValidator;
import com.example.templet.repository.CourseReactionRepository;
import com.example.templet.repository.CourseRepository;
import com.example.templet.repository.CourseSessionRepository;
import com.example.templet.repository.LocationRepository;
import com.example.templet.repository.dao.ReactionDao;
import com.example.templet.repository.model.Course;
import com.example.templet.repository.model.CourseReaction;
import com.example.templet.template.sucgestIAWithReaction.ReactionService;
import org.springframework.stereotype.Service;

@Service
public class CourseReactionService extends ReactionService {
  public CourseReactionService(
      CourseReactionRepository repository,
      ReactionValidator reactionValidator,
      ReactionDao<CourseReaction, Course> reactionDao,
      CourseService courseService,
      CourseRepository courseRepository,
      CourseSessionRepository courseSessionRepository,
      LocationRepository locationRepository) {
    super(
        repository,
        reactionValidator,
        reactionDao,
        courseService,
        courseRepository,
        courseSessionRepository,
        locationRepository);
  }
}
