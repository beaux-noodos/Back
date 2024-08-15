package com.example.templet.repository.model;

import com.example.templet.template.chat.IsInChat;
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
@Table(name = "\"course_session\"")
public class CourseSession extends HaveReaction implements Serializable, IsInChat {
  @Id private String id;
  @CreationTimestamp private Instant creationDatetime = Instant.now();
  @UpdateTimestamp private Instant lastUpdateDatetime;

  @ManyToOne
  @JoinColumn(name = "course_id")
  @JsonIgnore
  private Course course;

  @ManyToOne
  @JoinColumn(name = "location_id")
  @JsonIgnore
  private Location location;

  @ManyToOne
  @JoinColumn(name = "professor_id")
  @JsonIgnore
  private User professor;

  private String title;
  private String description;
  private Instant startDatetime;
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
