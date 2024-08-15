package com.example.templet.repository.model;

import com.example.templet.model.enums.Role;
import com.example.templet.model.enums.Sex;
import com.example.templet.model.enums.UserStatus;
import com.example.templet.template.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "\"user\"")
public class User implements Serializable, EntityModel {
  @Id private String id;
  private String firstname;
  private String lastname;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email must be valid")
  @Column(unique = true)
  private String mail;

  private LocalDate birthdate;

  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Enumerated(EnumType.STRING)
  private Role role;

  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Enumerated(EnumType.STRING)
  private Sex sex;

  @CreationTimestamp private Instant creationDatetime = Instant.now();
  @UpdateTimestamp private Instant lastUpdateDatetime;

  private String photoKey;
  private String photoBannerKey;
  private String username;

  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Enumerated(EnumType.STRING)
  private UserStatus status;

  @ManyToMany(mappedBy = "interestedUsers", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Course> coursesInterest;

  @ManyToMany(mappedBy = "followers", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Course> coursesFolow;
}
