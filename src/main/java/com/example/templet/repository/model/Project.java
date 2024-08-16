package com.example.templet.repository.model;

import com.example.templet.model.enums.ProjectStatus;
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
@Table(name = "\"project\"")
public class Project extends HaveReaction implements Serializable, HavePicture, IsInChat {
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
      name = "project_project_category", // Nom de la table de jointure
      joinColumns = @JoinColumn(name = "project_category_id"), // Colonne pour Project
      inverseJoinColumns = @JoinColumn(name = "project_id") // Colonne pour ProjectCategory
      )
  private List<ProjectCategory> projectCategorise;

  @ManyToOne
  @JoinColumn(name = "investor_id")
  @JsonIgnore
  private User investor;

  @ManyToOne
  @JoinColumn(name = "technical_solution_id")
  @JsonIgnore
  private User technicalSolution;

  @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
  private List<ProjectSession> sessions;

  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Enumerated(EnumType.STRING)
  private ProjectStatus projectStatus;

  private String imageKey;
  private double price;

  private boolean pictureIsImplemented;

  @ManyToOne
  @JoinColumn(name = "localisation_id")
  @JsonIgnore
  private Location localisation;

  private boolean investorNeed;
  private boolean technicalSolutionNeed;

  @UpdateTimestamp private Instant endDatetime;
  @UpdateTimestamp private Instant startDatetime;

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
    return "project";
  }

  @Override
  public String getData() {
    String category = "";
    for (ProjectCategory projectCategory : projectCategorise) {
      category += projectCategory.getName() + ",";
    }
    int coursHeure = 0;
    for (ProjectSession projectSession : sessions) {
      int heurs = projectSession.getStartDatetime().compareTo(projectSession.getEndDatetime());
      if (heurs > 0) {
        coursHeure += heurs;
      } else {
        coursHeure -= heurs;
      }
    }
    return " projet de : "
        + this.getTitle()
        + " "
        + this.getDescription()
        + "/"
        + " crer par par : "
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
        + (this.investor == null
            ? ""
            : (" investi par  : "
                + this.investor.getLastname()
                + " "
                + this.investor.getFirstname()))
        + "/"
        + (this.technicalSolution == null
            ? ""
            : (" solutioner thechiqueent par : "
                + this.technicalSolution.getLastname()
                + " "
                + this.technicalSolution.getFirstname()))
        + "/"
        + "\n";
  }

  @Override
  public long getAssessmentPoint() {
    return 0;
  }
}
