package com.example.templet.repository.model;

import com.example.templet.model.enums.CourseStatus;
import com.example.templet.template.chat.IsInChat;
import com.example.templet.template.file.HavePicture;
import com.example.templet.template.sucgestIAWithReaction.HaveReaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
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
@Table(name = "\"course\"")
public class Course extends HaveReaction implements Serializable, HavePicture, IsInChat {
  @Id private String id;
  @CreationTimestamp private Instant creationDatetime = Instant.now();
  @UpdateTimestamp private Instant lastUpdateDatetime;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  private String title;
  private String description;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "course_course_category", // Nom de la table de jointure
      joinColumns = @JoinColumn(name = "course_category_id"), // Colonne pour Course
      inverseJoinColumns = @JoinColumn(name = "course_id") // Colonne pour CourseCategory
      )
  private List<CourseCategory> courseCategorise;

  @JoinTable(
      name = "course_interested",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  @ManyToMany(fetch = FetchType.EAGER)
  private List<User> followers;

  @JoinTable(
      name = "course_followers",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  @ManyToMany(fetch = FetchType.EAGER)
  private List<User> interestedUsers;

  @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
  private List<CourseSession> sessions;

  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Enumerated(EnumType.STRING)
  private CourseStatus courseStatus;

  private String imageKey;
  private double price;

  private boolean pictureIsImplemented;

  @Override
  public Boolean getPictureIsImplemented() {
    return this.pictureIsImplemented;
  }

  @Override
  public void setPictureIsImplemented(Boolean bool) {
    this.pictureIsImplemented = bool;
  }

  @Override
  public String getDirectory() {
    return "course";
  }

  @Override
  public String getData() {
    String category = "";
    for (CourseCategory courseCategory : courseCategorise) {
      category += courseCategory.getName() + ",";
    }
    int coursHeure = 0;
    for (CourseSession courseSession : sessions) {
      int heurs = courseSession.getStartDatetime().compareTo(courseSession.getEndDatetime());
      if (heurs > 0) {
        coursHeure += heurs;
      } else {
        coursHeure -= heurs;
      }
    }
    return " cours de : "
        + this.getTitle()
        + " "
        + this.getDescription()
        + "/"
        + " enseignier par : "
        + this.user.getFirstname()
        + " "
        + this.user.getLastname()
        + "/"
        + " à propos des themes : "
        + category
        + "/"
        + " nombres d'heure de ccours: "
        + coursHeure
        + "/"
        + " nombre de pesone qui sui le cours : "
        + this.followers.size()
        + "/"
        + " nomre de persogne qui sont interesser par le cours : "
        + this.interestedUsers.size()
        + "/"
        + "\n";
  }

  @Override
  public long getAssessmentPoint() {
    return 0;
  }
}
