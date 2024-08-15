package com.example.templet.model.validator;

import com.example.templet.model.exception.BadRequestException;
import com.example.templet.template.EntityModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EntityModelValidator implements Consumer<EntityModel> {
  public void accept(List<EntityModel> entityModels) {
    entityModels.forEach(this);
  }

  @Override
  public void accept(EntityModel entityModel) {
    Set<String> violationMessages = new HashSet<>();

    if (entityModel.getId() == null) {
      violationMessages.add("User ID is mandatory");
    }

    if (!violationMessages.isEmpty()) {
      String formattedViolationMessages =
          violationMessages.stream().map(String::toString).collect(Collectors.joining(". "));
      throw new BadRequestException(formattedViolationMessages);
    }
  }
}
