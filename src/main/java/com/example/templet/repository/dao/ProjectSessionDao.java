package com.example.templet.repository.dao;

import com.example.templet.repository.model.Project;
import com.example.templet.repository.model.ProjectSession;
import com.example.templet.repository.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProjectSessionDao {
  private EntityManager entityManager;

  public List<ProjectSession> getUsersProjectSessions(String userId, Pageable pageable) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<ProjectSession> query = builder.createQuery(ProjectSession.class);
    Root<ProjectSession> root = query.from(ProjectSession.class);
    Join<ProjectSession, Project> projectSessionProjectJoin = root.join("project", JoinType.LEFT);
    Join<Project, User> projectUserJoin =
        projectSessionProjectJoin.join("followers", JoinType.LEFT);
    Predicate predicate = builder.equal(projectUserJoin.get("id"), userId);

    query
        .distinct(true)
        .where(predicate)
        .orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder))
        .orderBy(builder.desc(root.get("startDatetime")));

    return entityManager
        .createQuery(query)
        .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
        .setMaxResults(pageable.getPageSize())
        .getResultList();
  }
}
