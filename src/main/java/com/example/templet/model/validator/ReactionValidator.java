package com.example.templet.model.validator;

import com.example.templet.constant.ConfigurationConstant;
import com.example.templet.model.exception.BadRequestException;
import com.example.templet.template.sucgestIAWithReaction.Reaction;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReactionValidator implements Consumer<Reaction> {
  public void accept(List<Reaction> reactions) {
    reactions.forEach(this);
  }

  @Override
  public void accept(Reaction reaction) {
    Set<String> violationMessages = new HashSet<>();

    if (reaction.getId() == null) {
      violationMessages.add("User ID is mandatory");
    }
    if (reaction.getSubject() == null) {
      violationMessages.add("Subject is mandatory");
    }
    if (reaction.getStarsNumber() != null) {
      if (reaction.getStarsNumber() > ConfigurationConstant.MAX_STARS) {
        violationMessages.add(
            "Stars must be less than or equal to " + ConfigurationConstant.MAX_STARS);
      }
    }

    if (!violationMessages.isEmpty()) {
      String formattedViolationMessages =
          violationMessages.stream().map(String::toString).collect(Collectors.joining(". "));
      throw new BadRequestException(formattedViolationMessages);
    }
  }
}
