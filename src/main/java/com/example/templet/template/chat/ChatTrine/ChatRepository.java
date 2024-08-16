package com.example.templet.template.chat.ChatTrine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
  Chat findByUserId(String userId);
}
