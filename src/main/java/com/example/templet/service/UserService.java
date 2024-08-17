package com.example.templet.service;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.enums.ActionEnum;
import com.example.templet.model.enums.Role;
import com.example.templet.model.exception.BadRequestException;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.model.validator.UserValidator;
import com.example.templet.repository.ProjectRepository;
import com.example.templet.repository.UserRepository;
import com.example.templet.repository.model.Project;
import com.example.templet.repository.model.User;
import com.example.templet.template.chat.ChatTrine.Chat;
import com.example.templet.template.chat.ChatTrine.ChatRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository repository;
  private final ProjectRepository projectRepository;
  private UserValidator userValidator;
  private ChatRepository chatRepository;

  public List<User> findAllByName(String name, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.getUserByName(name, pageable);
  }

  @Transactional
  public User save(User toSave) {
    userValidator.accept(toSave);
    User newUser = repository.save(toSave);
    addChatIf(newUser);
    return newUser;
  }

  public void addChatIf(User user) {
    if (user.getRole().equals(Role.TECHNICAL_SOLUTION)) {
      if (chatRepository.findByUserId(user.getId()) == null) {
        Chat chat = Chat.builder().id(UUID.randomUUID().toString()).user(user).build();
        chatRepository.save(chat);
      }
    }
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
    User newUser = repository.save(user);
    addChatIf(newUser);
    return newUser;
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

  public Project userFolowAction(String userId, String projectId, ActionEnum actionEnum) {
    Optional<Project> projectOptional = projectRepository.findById(projectId);
    if (projectOptional.isEmpty()) {
      throw new NotFoundException("Projects with id " + projectId + " not found");
    }
    Optional<User> userOptional = repository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new NotFoundException("User with id " + userId + " not found");
    }
    Project project = projectOptional.get();
    User user = userOptional.get();
    User investor = project.getInvestor();
    User technicalSolution = project.getTechnicalSolution();
    if (investor != null) {
      if (actionEnum.equals(ActionEnum.INVEST)) {
        throw new BadRequestException("Have already an investor");
      }
    } else {
      if (actionEnum.equals(ActionEnum.INVEST)) {
        project.setInvestor(user);
      }
      if (actionEnum.equals(ActionEnum.UN_INVEST)) {
        project.setInvestor(user);
      }
    }
    if (technicalSolution != null) {
      if (actionEnum.equals(ActionEnum.GIVE_SOLUTION)) {
        throw new BadRequestException("Have already an technical solution");
      }
    } else {
      if (actionEnum.equals(ActionEnum.GIVE_SOLUTION)) {
        project.setInvestor(technicalSolution);
      }
      if (actionEnum.equals(ActionEnum.UN_GIVE_SOLUTION)) {
        project.setInvestor(null);
      }
    }
    return projectRepository.save(project);
  }
}
