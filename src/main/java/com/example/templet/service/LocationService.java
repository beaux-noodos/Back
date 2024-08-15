package com.example.templet.service;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.model.validator.EntityModelValidator;
import com.example.templet.repository.LocationRepository;
import com.example.templet.repository.model.Location;
import com.example.templet.template.sucgestIAWithReaction.HaveReaction;
import com.example.templet.template.sucgestIAWithReaction.HaveReactionService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LocationService extends HaveReactionService {
  private final LocationRepository repository;
  private EntityModelValidator entityModelValidator;

  public List<Location> findAll() {
    return repository.findAll();
  }

  public List<Location> findAll(PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllWithPage(pageable);
  }

  @Transactional
  public Location save(Location toSave) {
    entityModelValidator.accept(toSave);
    return repository.save(toSave);
  }

  @Override
  public Location save(HaveReaction toSave) {
    Location location = (Location) toSave;
    entityModelValidator.accept(toSave);
    return repository.save(location);
  }

  @Transactional
  public Location crupdateLocation(Location location, String locationId) {
    entityModelValidator.accept(location);
    Optional<Location> locationOptional = repository.findById(locationId);
    if (locationOptional.isPresent()) {
      Location locationFromDomain = locationOptional.get();
      location.setCreationDatetime(locationFromDomain.getCreationDatetime());
    }
    return repository.save(location);
  }

  @Override
  public Location findById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Location with id " + id + " not found"));
  }

  public Boolean checkSelfLocation(String locationId, String locationIdFromEndpoint) {
    Optional<Location> locationOptional = repository.findById(locationIdFromEndpoint);
    if (locationOptional.isEmpty()) {
      return true;
    }
    Location location = locationOptional.get();
    return location.getId().equals(locationId);
  }
}
