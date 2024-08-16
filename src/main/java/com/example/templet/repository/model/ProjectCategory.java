package com.example.templet.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
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
@Table(name = "\"project_category\"")
public class ProjectCategory {
  @Id private String id;
  @CreationTimestamp private Instant creationDatetime = Instant.now();
  @UpdateTimestamp private Instant lastUpdateDatetime;

  private String name;
  private String description;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projectCategorise")
  @JsonIgnore
  private List<Project> projects;
}
