package com.example.templet.service;

import com.example.templet.model.validator.ReactionValidator;
import com.example.templet.repository.LocationRepository;
import com.example.templet.repository.ProjectReactionRepository;
import com.example.templet.repository.ProjectRepository;
import com.example.templet.repository.ProjectSessionRepository;
import com.example.templet.repository.dao.ReactionDao;
import com.example.templet.repository.model.Project;
import com.example.templet.repository.model.ProjectReaction;
import com.example.templet.template.sucgestIAWithReaction.ReactionService;
import org.springframework.stereotype.Service;

@Service
public class ProjectReactionService extends ReactionService {
  public ProjectReactionService(
      ProjectReactionRepository repository,
      ReactionValidator reactionValidator,
      ReactionDao<ProjectReaction, Project> reactionDao,
      ProjectService projectService,
      ProjectRepository projectRepository,
      ProjectSessionRepository projectSessionRepository,
      LocationRepository locationRepository) {
    super(
        repository,
        reactionValidator,
        reactionDao,
        projectService,
        projectRepository,
        projectSessionRepository,
        locationRepository);
  }
}
