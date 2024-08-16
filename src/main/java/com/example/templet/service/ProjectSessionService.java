package com.example.templet.service;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.model.validator.EntityModelValidator;
import com.example.templet.repository.ProjectSessionRepository;
import com.example.templet.repository.dao.ProjectSessionDao;
import com.example.templet.repository.model.ProjectSession;
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
public class ProjectSessionService extends HaveReactionService {
  private final ProjectSessionRepository repository;
  private final ProjectSessionDao projectSessionDao;
  private EntityModelValidator entityModelValidator;

  public List<ProjectSession> findAll() {
    return repository.findAll();
  }

  public List<ProjectSession> findAll(PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllWithPage(pageable);
  }

  @Transactional
  public ProjectSession save(ProjectSession toSave) {
    entityModelValidator.accept(toSave);
    return repository.save(toSave);
  }

  @Override
  public ProjectSession save(HaveReaction toSave) {
    ProjectSession projectSession = (ProjectSession) toSave;
    entityModelValidator.accept(toSave);
    return repository.save(projectSession);
  }

  @Transactional
  public ProjectSession crupdateProjectSession(
      ProjectSession projectSession, String projectSessionId) {
    entityModelValidator.accept(projectSession);
    Optional<ProjectSession> projectSessionOptional = repository.findById(projectSessionId);
    if (projectSessionOptional.isPresent()) {
      ProjectSession projectSessionFromDomain = projectSessionOptional.get();
      if (projectSession.getLocation() == null) {
        projectSession.setLocation(projectSessionFromDomain.getLocation());
      }
      if (projectSession.getProject() == null) {
        projectSession.setProject(projectSessionFromDomain.getProject());
      }
      projectSession.setCreationDatetime(projectSessionFromDomain.getCreationDatetime());
    }
    return repository.save(projectSession);
  }

  @Override
  public ProjectSession findById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("ProjectSession with id " + id + " not found"));
  }

  public Boolean checkSelfProjectSession(
      String projectSessionId, String projectSessionIdFromEndpoint) {
    Optional<ProjectSession> projectSessionOptional =
        repository.findById(projectSessionIdFromEndpoint);
    if (projectSessionOptional.isEmpty()) {
      return true;
    }
    ProjectSession projectSession = projectSessionOptional.get();
    return projectSession.getId().equals(projectSessionId);
  }

  public List<ProjectSession> findAllByProject(
      String projectId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());

    return repository.findAllByProjectId(projectId, pageable);
  }

  public List<ProjectSession> findAllByLocation(
      String locationId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllByLocationId(locationId, pageable);
  }

  public List<ProjectSession> getUsersProjectSessions(
      String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.getUsersProjectSessions(userId, pageable);
  }
}
