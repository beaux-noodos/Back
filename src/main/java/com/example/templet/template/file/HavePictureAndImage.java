package com.example.templet.template.file;

import com.example.templet.template.sucgestIAWithReaction.HaveReaction;
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
public abstract class HavePictureAndImage extends HaveReaction {
  private String ImageKey;
  @Id private String id;
}
