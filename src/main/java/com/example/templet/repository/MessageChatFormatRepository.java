package com.example.templet.repository;

import com.example.templet.repository.model.MessageChatFormatEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageChatFormatRepository
    extends JpaRepository<MessageChatFormatEntity, String> {
  @Query(
      "SELECT m FROM MessageChatFormatEntity m where m.user.id = :userId and m.chat is null order"
          + " by m.creationDatetime")
  List<MessageChatFormatEntity> findByUserIdAndChatIsEmptyOrderByCreationDatetimeDesc(
      String userId, Pageable pageable);

  @Query(
      "SELECT m FROM MessageChatFormatEntity m where m.user.id = :userId and m.chat.id = :chatId"
          + " order by m.creationDatetime")
  List<MessageChatFormatEntity> findByUserIdAndChatIdOrderByCreationDatetimeDesc(
      String userId, String chatId, Pageable pageable);
}
