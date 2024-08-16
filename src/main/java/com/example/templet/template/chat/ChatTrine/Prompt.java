package com.example.templet.template.chat.ChatTrine;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "prompt")
public class Prompt implements Serializable {

  @Id private String id;

  @CreationTimestamp private Instant creationDatetime;

  @UpdateTimestamp private Instant updatedAt;

  private String promptCategory;

  private String body;

  @ManyToOne
  @JoinColumn(name = "chat_id")
  private Chat chat;

  @PrePersist
  protected void onCreate() {
    this.creationDatetime = Instant.now();
    this.updatedAt = Instant.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = Instant.now();
  }
}
