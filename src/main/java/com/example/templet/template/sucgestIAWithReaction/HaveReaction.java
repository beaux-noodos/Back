package com.example.templet.template.sucgestIAWithReaction;

import com.example.templet.template.EntityModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public abstract class HaveReaction implements EntityModel {
  private long likeNumber;
  private long dislikeNumber;
  private long viewNumber;
  private long starNumber;
  private double starMedium;
  //  private long firstPoint; todo add this for performance
  @Id private String id;
}
