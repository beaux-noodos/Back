package com.example.templet.template.sucgestIAWithReaction;

import com.example.templet.model.enums.ReactionType;
import com.example.templet.repository.model.User;
import com.example.templet.template.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class Reaction {
  @Id private String id;
  @CreationTimestamp private Instant creationDatetime;
  @UpdateTimestamp private Instant lastUpdateDatetime;

  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Enumerated(EnumType.STRING)
  private ReactionType likeReaction;

  private Boolean vision;
  private Integer starsNumber;
  private String comment;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  public abstract EntityModel getSubject();

  public abstract Reaction setSubject(EntityModel id);
}
