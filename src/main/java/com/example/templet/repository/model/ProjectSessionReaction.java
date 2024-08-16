package com.example.templet.repository.model;

import com.example.templet.template.EntityModel;
import com.example.templet.template.sucgestIAWithReaction.Reaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSessionReaction extends Reaction implements Serializable {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_session_id")
  @JsonIgnore
  private ProjectSession subject;

  @Override
  public Reaction setSubject(EntityModel subject) {
    this.subject = (ProjectSession) subject;
    return this;
  }
}
