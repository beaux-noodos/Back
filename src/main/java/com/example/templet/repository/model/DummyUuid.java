package com.example.templet.repository.model;

import com.example.templet.template.EntityModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DummyUuid implements EntityModel {
  @Id private String id;
}
