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
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NotificationReaction extends Reaction implements Serializable {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "notification_id")
  @JsonIgnore
  private Notification subject;

  @Override
  public Reaction setSubject(EntityModel subject) {
    this.subject = (Notification) subject;
    return this;
  }

  @Override
  public EntityModel getSubject() {
    return this.subject;
  }
}
