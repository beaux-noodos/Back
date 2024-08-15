package com.example.templet.repository;

import com.example.templet.repository.model.Notification;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
  @Override
  List<Notification> findAll();

  @Query("SELECT x FROM Notification x ")
  List<Notification> findAllWithPage(Pageable pageable);

  @Override
  Optional<Notification> findById(String id);

  List<Notification> findAllByUserId(String userId, Pageable pageable);
}
