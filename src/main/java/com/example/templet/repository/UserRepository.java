package com.example.templet.repository;

import com.example.templet.repository.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query(
      "SELECT u FROM User u "
          + "WHERE LOWER(COALESCE(u.firstname, '')) LIKE LOWER(CONCAT('%', :name, '%'))"
          + "OR LOWER(COALESCE(u.lastname, '')) LIKE LOWER(CONCAT('%', :name, '%')) "
          + "OR LOWER(COALESCE(u.username, '')) LIKE LOWER(CONCAT('%', :name, '%'))")
  List<User> getUserByName(@Param(value = "name") String name, Pageable pageable);

  Optional<User> findByMail(String mail);
}
