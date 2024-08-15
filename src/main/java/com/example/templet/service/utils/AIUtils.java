package com.example.templet.service.utils;

import static com.example.templet.template.chat.AIConstant.NOT_OVER_FOLL_COEFFICIENT;

import java.util.ArrayList;
import java.util.List;

public class AIUtils {
  public static Long takeSymbolicAiPoint(long maxPoint, long basePoint, Long number) {
    // number * basePoint must be less than NOT_OVER_FOLL_COEFFICIENT
    long numberModifyToNoHaveOverfowProblem = number * basePoint;
    return (numberModifyToNoHaveOverfowProblem * maxPoint * 3) / (NOT_OVER_FOLL_COEFFICIENT * 4)
        + (maxPoint / 4);
  }

  public static long passesThrough0WithFiniteLimit(long limit, long speed, long x) {
    return (limit - limit / ((long) (Math.sqrt(((double) (x * speed / limit)) + limit))));
  }

  public static <T> List<T> getPaginatedList(List<T> list, int page, int pageSize) {
    if (list == null || list.isEmpty() || pageSize <= 0 || page <= 0) {
      return new ArrayList<>(); // Retourner une liste vide en cas d'arguments invalides
    }

    int fromIndex = (page - 1) * pageSize;
    int toIndex = Math.min(fromIndex + pageSize, list.size());

    if (fromIndex >= list.size()) {
      return new ArrayList<>(); // Retourner une liste vide si l'index de départ est hors des
      // limites
    }

    return list.subList(fromIndex, toIndex);
  }
}
