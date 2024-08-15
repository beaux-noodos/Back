package com.example.templet.repository;

import com.example.templet.repository.model.MessageChatFormatEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageChatFormatRepository
    extends JpaRepository<MessageChatFormatEntity, String> {
  List<MessageChatFormatEntity> findByUserIdOrderByCreationDatetimeDesc(
      String userId, Pageable pageable);
}
