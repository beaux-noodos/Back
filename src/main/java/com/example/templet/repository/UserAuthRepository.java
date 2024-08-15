package com.example.templet.repository;

import com.example.templet.repository.model.UserAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, String> {
  UserAuth getByPasswordAndUserMail(String password, String userMail);

  Optional<UserAuth> findByUserMail(String userMail);
}
