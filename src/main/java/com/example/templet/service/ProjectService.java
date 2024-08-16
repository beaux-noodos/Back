package com.example.templet.service;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.model.validator.EntityModelValidator;
import com.example.templet.repository.ProjectReactionRepository;
import com.example.templet.repository.ProjectRepository;
import com.example.templet.repository.model.Project;
import com.example.templet.service.utils.AIUtils;
import com.example.templet.template.chat.DBChat.IsInChat;
import com.example.templet.template.chat.DBChat.IsInChatService;
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
public class ProjectService extends HaveReactionService
    implements HavePictureService, IsInChatService {
  private final ProjectRepository repository;
  private final ProjectReactionRepository xxxxxReactionRepository;
  private EntityModelValidator entityModelValidator;

  public List<Project> findAll() {
    return repository.findAll();
  }

  public List<Project> findAll(PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllWithPage(pageable);
  }

  @Transactional
  public Project save(Project toSave) {
    entityModelValidator.accept(toSave);
    return repository.save(toSave);
  }

  @Override
  @Transactional
  public Project save(HaveReaction toSave) {
    Project project = (Project) toSave;
    entityModelValidator.accept(toSave);
    return repository.save(project);
  }

  @Override
  @Transactional
  public HavePicture save(HavePicture havePicture) {
    Project project = (Project) havePicture;
    entityModelValidator.accept(project);
    return repository.save(project);
  }

  @Transactional
  public Project crupdateProject(Project project, String projectId) {
    entityModelValidator.accept(project);
    Optional<Project> projectOptional = repository.findById(projectId);
    if (projectOptional.isPresent()) {
      Project projectFromDomain = projectOptional.get();
      if (project.getUser() == null) {
        project.setUser(projectFromDomain.getUser());
      }
      project.setCreationDatetime(projectFromDomain.getCreationDatetime());
    }
    return repository.save(project);
  }

  @Override
  public Project findById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Project with id " + id + " not found"));
  }

  public Boolean checkSelfProject(String projectId, String projectIdFromEndpoint) {
    Optional<Project> projectOptional = repository.findById(projectIdFromEndpoint);
    if (projectOptional.isEmpty()) {
      return true;
    }
    Project project = projectOptional.get();
    return project.getId().equals(projectId);
  }

  public List<Project> findAllByUserId(String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllByUserId(userId, pageable);
  }

  public List<Project> findSuggestAi(String userId, PageFromOne page, BoundedPageSize pageSize) {
    int pageValue = page.getValue();
    int pageSizeValue = pageSize.getValue();
    Pageable pageableXxx = PageRequest.of(0, 500);

    List<Project> projects = repository.findAllByUserId(userId, pageableXxx);

    Collections.sort(
        projects,
        new Comparator<Project>() {
          @Override
          public int compare(Project p1, Project p2) {
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

    return AIUtils.getPaginatedList(projects, pageValue, pageSizeValue);
  }

  public List<Project> findAllProjectsInvestedInByUserId(
      String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllProjectsInvestedInByUserId(userId, pageable);
  }

  public List<Project> findAllProjectsSolvedByUserId(
      String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllProjectsSolvedByUserId(userId, pageable);
  }

  @Override
  public List<IsInChat> getIsInChatToConsider() {
    return repository.findAll().stream().map(project -> (IsInChat) project).toList();
  }
}
