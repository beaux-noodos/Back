package com.example.templet.repository.dao;

import com.example.templet.repository.model.User;
import com.example.templet.template.EntityModel;
import com.example.templet.template.sucgestIAWithReaction.Reaction;
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
public class ReactionDao<T extends Reaction, Y extends EntityModel> {
  private EntityManager entityManager;

  public List<T> findByCriteria(
      String subjectId,
      String userId,
      Boolean havelikeReaction,
      Boolean haveVision,
      Boolean haveStarsNumber,
      Boolean haveComment,
      Pageable pageable,
      Class<T> clazz) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> query = builder.createQuery(clazz);
    Root<T> root = query.from(clazz);
    Join<T, User> reactionUserJoin = root.join("user", JoinType.LEFT);
    Join<T, Y> reactionEntityModelJoin = reactionUserJoin.join("subject", JoinType.LEFT);

    Predicate predicate = builder.conjunction();
    predicate = builder.equal(builder.lower(reactionEntityModelJoin.get("id")), subjectId);

    if (userId != null && !userId.isEmpty()) {
      predicate =
          builder.and(
              predicate,
              builder.and(builder.equal(builder.lower(reactionUserJoin.get("id")), userId)));
    }

    if (havelikeReaction != null && havelikeReaction) {
      predicate = builder.and(predicate, builder.isNotNull(root.get("likeReaction")));
    }
    if (haveVision != null && haveVision) {
      predicate = builder.and(predicate, builder.isTrue(root.get("vision")));
    }
    if (haveStarsNumber != null && haveStarsNumber) {
      predicate = builder.and(predicate, builder.isNotNull(root.get("starsNumber")));
    }
    if (haveComment != null && haveComment) {
      predicate = builder.and(predicate, builder.isNotNull(root.get("comment")));
    }

    query
        .distinct(true)
        .where(predicate)
        .orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder))
        .orderBy(builder.desc(root.get("creationDatetime")));

    return entityManager
        .createQuery(query)
        .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
        .setMaxResults(pageable.getPageSize())
        .getResultList();
  }
}
