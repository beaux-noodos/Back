package com.example.templet.template.sucgestIAWithReaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public abstract class HaveReactionService {
  public abstract HaveReaction findById(String id);

  public abstract HaveReaction save(HaveReaction haveReaction);
}
