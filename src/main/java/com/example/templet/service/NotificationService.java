package com.example.templet.service;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.model.validator.EntityModelValidator;
import com.example.templet.repository.NotificationReactionRepository;
import com.example.templet.repository.NotificationRepository;
import com.example.templet.repository.model.Notification;
import com.example.templet.service.utils.AIUtils;
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
public class NotificationService {
  private final NotificationRepository repository;
  private final NotificationReactionRepository xxxxxReactionRepository;
  private EntityModelValidator entityModelValidator;

  public List<Notification> findAll() {
    return repository.findAll();
  }

  public List<Notification> findAll(PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllWithPage(pageable);
  }

  @Transactional
  public Notification save(Notification toSave) {
    entityModelValidator.accept(toSave);
    return repository.save(toSave);
  }

  @Transactional
  public Notification crupdatnotificatione(Notification notification, String notificationId) {
    entityModelValidator.accept(notification);
    Optional<Notification> notificationOptional = repository.findById(notificationId);
    if (notificationOptional.isPresent()) {
      Notification notificationFromDomain = notificationOptional.get();
      if (notification.getUser() == null) {
        notification.setUser(notificationFromDomain.getUser());
      }
      notification.setCreationDatetime(notificationFromDomain.getCreationDatetime());
    }
    return repository.save(notification);
  }

  public Notification findById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Notification with id " + id + " not found"));
  }

  public Boolean checkSelfNotification(String notificationId, String notificationIdFromEndpoint) {
    Optional<Notification> notificationOptional = repository.findById(notificationIdFromEndpoint);
    if (notificationOptional.isEmpty()) {
      return true;
    }
    Notification notification = notificationOptional.get();
    return notification.getId().equals(notificationId);
  }

  public List<Notification> findAllByUserId(
      String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllByUserId(userId, pageable);
  }

  public List<Notification> findSuggestAi(
      String userId, PageFromOne page, BoundedPageSize pageSize) {
    int pageValue = page.getValue();
    int pageSizeValue = pageSize.getValue();
    Pageable pageableXxx = PageRequest.of(0, 500);

    List<Notification> notifications = repository.findAllByUserId(userId, pageableXxx);

    Collections.sort(
        notifications,
        new Comparator<Notification>() {
          @Override
          public int compare(Notification p1, Notification p2) {
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

    return AIUtils.getPaginatedList(notifications, pageValue, pageSizeValue);
  }
}
