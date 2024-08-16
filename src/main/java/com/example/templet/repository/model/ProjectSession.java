package com.example.templet.repository.model;

import com.example.templet.template.chat.DBChat.IsInChat;
import com.example.templet.template.sucgestIAWithReaction.HaveReaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "\"project_session\"")
public class ProjectSession extends HaveReaction implements Serializable, IsInChat {
  @Id private String id;
  @CreationTimestamp private Instant creationDatetime = Instant.now();
  @UpdateTimestamp private Instant lastUpdateDatetime;

  @ManyToOne
  @JoinColumn(name = "project_id")
  @JsonIgnore
  private Project project;

  @ManyToOne
  @JoinColumn(name = "location_id")
  @JsonIgnore
  private Location location;

  private String title;
  private String description;
  private Instant endDatetime;

  @Override
  public String getData() {
    return "";
  }

  @Override
  public long getAssessmentPoint() {
    return 0;
  }
}
