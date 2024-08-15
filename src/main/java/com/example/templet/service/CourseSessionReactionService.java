package com.example.templet.service;

import com.example.templet.model.validator.ReactionValidator;
import com.example.templet.repository.CourseRepository;
import com.example.templet.repository.CourseSessionReactionRepository;
import com.example.templet.repository.CourseSessionRepository;
import com.example.templet.repository.LocationRepository;
import com.example.templet.repository.dao.ReactionDao;
import com.example.templet.repository.model.CourseSession;
import com.example.templet.repository.model.CourseSessionReaction;
import com.example.templet.template.sucgestIAWithReaction.ReactionService;
import org.springframework.stereotype.Service;

@Service
public class CourseSessionReactionService extends ReactionService {
  public CourseSessionReactionService(
      CourseSessionReactionRepository repository,
      ReactionValidator reactionValidator,
      ReactionDao<CourseSessionReaction, CourseSession> reactionDao,
      CourseSessionService courseSessionService,
      CourseRepository courseRepository,
      CourseSessionRepository courseSessionRepository,
      LocationRepository locationRepository) {
    super(
        repository,
        reactionValidator,
        reactionDao,
        courseSessionService,
        courseRepository,
        courseSessionRepository,
        locationRepository);
  }
}
