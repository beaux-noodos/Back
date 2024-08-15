package com.example.templet.repository;

import com.example.templet.repository.model.Signalisation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SignalisationRepository extends JpaRepository<Signalisation, String> {
  @Override
  List<Signalisation> findAll();

  @Query("SELECT x FROM Signalisation x ")
  List<Signalisation> findAllWithPage(Pageable pageable);

  @Override
  Optional<Signalisation> findById(String id);

  List<Signalisation> findAllByUserId(String userId, Pageable pageable);
}
