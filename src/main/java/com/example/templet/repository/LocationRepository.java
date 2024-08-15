package com.example.templet.repository;

import com.example.templet.repository.model.Location;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

  @Override
  List<Location> findAll();

  @Query("SELECT z FROM Location z ")
  List<Location> findAllWithPage(Pageable pageable);

  @Override
  Optional<Location> findById(String id);
}
