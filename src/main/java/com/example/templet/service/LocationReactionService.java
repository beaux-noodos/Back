package com.example.templet.service;

import com.example.templet.model.validator.ReactionValidator;
import com.example.templet.repository.LocationReactionRepository;
import com.example.templet.repository.LocationRepository;
import com.example.templet.repository.ProjectRepository;
import com.example.templet.repository.ProjectSessionRepository;
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
      ProjectRepository projectRepository,
      ProjectSessionRepository projectSessionRepository,
      LocationRepository locationRepository) {
    super(
        repository,
        reactionValidator,
        reactionDao,
        locationService,
        projectRepository,
        projectSessionRepository,
        locationRepository);
  }
}
