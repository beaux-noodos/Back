package com.example.templet.repository.model;

import com.example.templet.template.EntityModel;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "disc_type", length = 4)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Picture implements EntityModel {

  @Id private String id;

  private String bucketKey;

  @CreationTimestamp private Instant creationDatetime;
}
