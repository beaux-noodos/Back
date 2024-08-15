package com.example.templet.service;

import com.example.templet.model.validator.ReactionValidator;
import com.example.templet.repository.CourseRepository;
import com.example.templet.repository.CourseSessionRepository;
import com.example.templet.repository.LocationReactionRepository;
import com.example.templet.repository.LocationRepository;
import com.example.templet.repository.dao.ReactionDao;
import com.example.templet.repository.model.Location;
import com.example.templet.repository.model.LocationReaction;
import com.example.templet.template.sucgestIAWithReaction.ReactionService;
import org.springframework.stereotype.Service;

@Service
public class LocationReactionService extends ReactionService {
  public LocationReactionService(
      LocationReactionRepository repository,
      ReactionValidator reactionValidator,
      ReactionDao<LocationReaction, Location> reactionDao,
      LocationService locationService,
      CourseRepository courseRepository,
      CourseSessionRepository courseSessionRepository,
      LocationRepository locationRepository) {
    super(
        repository,
        reactionValidator,
        reactionDao,
        locationService,
        courseRepository,
        courseSessionRepository,
        locationRepository);
  }
}
