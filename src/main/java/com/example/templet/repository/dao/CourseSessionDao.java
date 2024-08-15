package com.example.templet.repository.dao;

import com.example.templet.repository.model.Course;
import com.example.templet.repository.model.CourseSession;
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
public class CourseSessionDao {
  private EntityManager entityManager;

  public List<CourseSession> getUsersCourseSessions(String userId, Pageable pageable) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<CourseSession> query = builder.createQuery(CourseSession.class);
    Root<CourseSession> root = query.from(CourseSession.class);
    Join<CourseSession, Course> courseSessionCourseJoin = root.join("course", JoinType.LEFT);
    Join<Course, User> courseUserJoin = courseSessionCourseJoin.join("followers", JoinType.LEFT);
    Predicate predicate = builder.equal(courseUserJoin.get("id"), userId);

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
