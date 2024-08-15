package com.example.templet.service;

import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.exception.NotFoundException;
import com.example.templet.model.validator.EntityModelValidator;
import com.example.templet.repository.SignalisationReactionRepository;
import com.example.templet.repository.SignalisationRepository;
import com.example.templet.repository.model.Signalisation;
import com.example.templet.service.utils.AIUtils;
import com.example.templet.template.sucgestIAWithReaction.SymbolicAIService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SignalisationService {
  private final SignalisationRepository repository;
  private final SignalisationReactionRepository xxxxxReactionRepository;
  private EntityModelValidator entityModelValidator;

  public List<Signalisation> findAll() {
    return repository.findAll();
  }

  public List<Signalisation> findAll(PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllWithPage(pageable);
  }

  @Transactional
  public Signalisation save(Signalisation toSave) {
    entityModelValidator.accept(toSave);
    return repository.save(toSave);
  }

  @Transactional
  public Signalisation crupdateSignalisation(Signalisation signalisation, String signalisationId) {
    entityModelValidator.accept(signalisation);
    Optional<Signalisation> signalisationOptional = repository.findById(signalisationId);
    if (signalisationOptional.isPresent()) {
      Signalisation signalisationFromDomain = signalisationOptional.get();
      if (signalisation.getUser() == null) {
        signalisation.setUser(signalisationFromDomain.getUser());
      }
      signalisation.setCreationDatetime(signalisationFromDomain.getCreationDatetime());
    }
    return repository.save(signalisation);
  }

  public Signalisation findById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Signalisation with id " + id + " not found"));
  }

  public Boolean checkSelsignalisationf(
      String signalisationId, String signalisationIdFromEndpoint) {
    Optional<Signalisation> signalisationOptional =
        repository.findById(signalisationIdFromEndpoint);
    if (signalisationOptional.isEmpty()) {
      return true;
    }
    Signalisation signalisation = signalisationOptional.get();
    return signalisation.getId().equals(signalisationId);
  }

  public List<Signalisation> findAllByUserId(
      String userId, PageFromOne page, BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return repository.findAllByUserId(userId, pageable);
  }

  public List<Signalisation> findSuggestAi(
      String userId, PageFromOne page, BoundedPageSize pageSize) {
    int pageValue = page.getValue();
    int pageSizeValue = pageSize.getValue();
    Pageable pageableXxx = PageRequest.of(0, 500);

    List<Signalisation> signalisations = repository.findAllByUserId(userId, pageableXxx);

    Collections.sort(
        signalisations,
        new Comparator<Signalisation>() {
          @Override
          public int compare(Signalisation p1, Signalisation p2) {
            long point1 =
                SymbolicAIService.generateUserPersonalPointInOneSubject(
                        new ArrayList<>(), new ArrayList<>())
                    * SymbolicAIService.getSubjectAIPoints(p1.getId(), xxxxxReactionRepository);
            long point2 =
                SymbolicAIService.generateUserPersonalPointInOneSubject(
                        new ArrayList<>(), new ArrayList<>())
                    * SymbolicAIService.getSubjectAIPoints(p2.getId(), xxxxxReactionRepository);

            return Integer.compare((int) (point1 / 1_000_000L), (int) (point2 / 1_000_000L));
          }
        });

    return AIUtils.getPaginatedList(signalisations, pageValue, pageSizeValue);
  }
}
