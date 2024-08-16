package com.example.templet.service;

import com.example.templet.model.validator.ReactionValidator;
import com.example.templet.repository.LocationRepository;
import com.example.templet.repository.ProjectRepository;
import com.example.templet.repository.ProjectSessionReactionRepository;
import com.example.templet.repository.ProjectSessionRepository;
import com.example.templet.repository.dao.ReactionDao;
import com.example.templet.repository.model.ProjectSession;
import com.example.templet.repository.model.ProjectSessionReaction;
import com.example.templet.template.sucgestIAWithReaction.ReactionService;
import org.springframework.stereotype.Service;

@Service
public class ProjectSessionReactionService extends ReactionService {
  public ProjectSessionReactionService(
      ProjectSessionReactionRepository repository,
      ReactionValidator reactionValidator,
      ReactionDao<ProjectSessionReaction, ProjectSession> reactionDao,
      ProjectSessionService projectSessionService,
      ProjectRepository projectRepository,
      ProjectSessionRepository projectSessionRepository,
      LocationRepository locationRepository) {
    super(
        repository,
        reactionValidator,
        reactionDao,
        projectSessionService,
        projectRepository,
        projectSessionRepository,
        locationRepository);
  }
}
