package com.example.templet.endpoint.mapper;

import static com.example.templet.service.utils.EnumMapperUtils.mapEnum;

import com.example.templet.constant.FileConstant;
import com.example.templet.endpoint.rest.model.Role;
import com.example.templet.endpoint.rest.model.Sex;
import com.example.templet.endpoint.rest.model.SignUp;
import com.example.templet.endpoint.rest.model.User;
import com.example.templet.endpoint.rest.model.UserStatus;
import com.example.templet.template.file.S3Service;
import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

  private final S3Service s3Service;

  public static com.example.templet.repository.model.User toDomain(User rest) {
    return com.example.templet.repository.model.User.builder()
        .id(rest.getId())
        .firstname(rest.getFirstName())
        .lastname(rest.getLastName())
        .birthdate(rest.getBirthDate())
        .lastUpdateDatetime(Instant.now())
        .mail(rest.getEmail())
        .username(rest.getUsername())
        .status(toDomain(rest.getStatus()))
        .sex(toDomain(rest.getSex()))
        .role(toDomain(rest.getRole()))
        .build();
  }

  public static com.example.templet.repository.model.User toDomain(SignUp rest) {
    return com.example.templet.repository.model.User.builder()
        .id(rest.getId())
        .firstname(rest.getFirstName())
        .lastname(rest.getLastName())
        .birthdate(rest.getBirthDate())
        .lastUpdateDatetime(Instant.now())
        .mail(rest.getEmail())
        .username(rest.getUsername())
        .status(toDomain(rest.getStatus()))
        .sex(toDomain(rest.getSex()))
        .role(toDomain(rest.getRole()))
        .creationDatetime(Instant.now())
        .build();
  }

  public User toRest(com.example.templet.repository.model.User domain) {
    String photoKey = domain.getPhotoKey();
    String Id = com.example.templet.repository.model.User.builder().id(domain.getId()).toString();
    if (Id == null) {
      return null;
    }
    return new User()
        .id(domain.getId())
        .firstName(domain.getFirstname())
        .lastName(domain.getLastname())
        .email(domain.getMail())
        .birthDate(domain.getBirthdate())
        .photoUrl(
            photoKey == null
                ? null
                : s3Service.generatePresignedUrl(photoKey, FileConstant.URL_DURATION).toString())
        .username(domain.getUsername())
        .status(convertToRest(domain.getStatus()))
        .entranceDatetime(domain.getCreationDatetime())
        .role(convertToRest(domain.getRole()))
        .sex(convertToRest(domain.getSex()));
  }

  public static Sex convertToRest(com.example.templet.model.enums.Sex sex) {
    if (sex == null) {
      return null;
    }
    return mapEnum(
        sex,
        Map.of(
            com.example.templet.model.enums.Sex.M, Sex.M,
            com.example.templet.model.enums.Sex.F, Sex.F,
            com.example.templet.model.enums.Sex.OTHER, Sex.OTHER));
  }

  public static UserStatus convertToRest(com.example.templet.model.enums.UserStatus userStatus) {
    if (userStatus == null) {
      return null;
    }
    return mapEnum(
        userStatus,
        Map.of(
            com.example.templet.model.enums.UserStatus.ENABLED, UserStatus.ENABLED,
            com.example.templet.model.enums.UserStatus.BANISHED, UserStatus.BANISHED));
  }

  public static Role convertToRest(com.example.templet.model.enums.Role role) {
    if (role == null) {
      return Role.CLIENT;
    }
    return mapEnum(
        role,
        Map.of(
            com.example.templet.model.enums.Role.CLIENT, Role.CLIENT,
            com.example.templet.model.enums.Role.MANAGER, Role.MANAGER));
  }

  public static com.example.templet.model.enums.Sex toDomain(Sex sex) {
    if (sex == null) {
      return null;
    }
    return mapEnum(
        sex,
        Map.of(
            Sex.M, com.example.templet.model.enums.Sex.M,
            Sex.F, com.example.templet.model.enums.Sex.F,
            Sex.OTHER, com.example.templet.model.enums.Sex.OTHER));
  }

  public static com.example.templet.model.enums.Role toDomain(Role role) {
    if (role == null) {
      return com.example.templet.model.enums.Role.CLIENT;
    }
    return mapEnum(
        role,
        Map.of(
            Role.CLIENT, com.example.templet.model.enums.Role.CLIENT,
            Role.MANAGER, com.example.templet.model.enums.Role.MANAGER));
  }

  public static com.example.templet.model.enums.UserStatus toDomain(UserStatus userStatus) {
    if (userStatus == null) {
      return com.example.templet.model.enums.UserStatus.ENABLED;
    }
    return mapEnum(
        userStatus,
        Map.of(
            UserStatus.ENABLED, com.example.templet.model.enums.UserStatus.ENABLED,
            UserStatus.BANISHED, com.example.templet.model.enums.UserStatus.BANISHED));
  }
}
