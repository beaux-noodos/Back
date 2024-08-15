package com.example.templet.service.utils;

import com.example.templet.model.exception.BadRequestException;
import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnumMapperUtils {
  public static <T, U> U mapEnum(T input, Map<T, U> enumMap) {
    if (input == null) {
      return null;
    }
    return enumMap.get(input);
  }

  public static <T extends Enum<T>, U extends Enum<U>> U mapEnum(
      Class<U> domainEnumClass, T clientEnum) {
    Map<String, U> map = new HashMap<>();
    for (U enumConstant : domainEnumClass.getEnumConstants()) {
      map.put(enumConstant.name(), enumConstant);
    }
    U result = map.get(clientEnum.name());
    if (result == null) {
      throw new BadRequestException("Enum '" + clientEnum.name() + "' not exist");
    }
    return result;
  }
}
