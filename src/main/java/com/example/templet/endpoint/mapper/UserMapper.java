package com.example.templet.endpoint.mapper;

import com.example.templet.constant.FileConstant;
import com.example.templet.endpoint.rest.model.Role;
import com.example.templet.endpoint.rest.model.Sex;
import com.example.templet.endpoint.rest.model.SignUp;
import com.example.templet.endpoint.rest.model.User;
import com.example.templet.endpoint.rest.model.UserStatus;
import com.example.templet.service.utils.EnumMapperUtils;
import com.example.templet.template.file.S3Service;
import java.time.Instant;
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
    return EnumMapperUtils.mapEnum(Sex.class, sex);
  }

  public static UserStatus convertToRest(com.example.templet.model.enums.UserStatus userStatus) {
    return EnumMapperUtils.mapEnum(UserStatus.class, userStatus);
  }

  public static Role convertToRest(com.example.templet.model.enums.Role role) {
    return EnumMapperUtils.mapEnum(Role.class, role);
  }

  public static com.example.templet.model.enums.Sex toDomain(Sex sex) {
    return EnumMapperUtils.mapEnum(com.example.templet.model.enums.Sex.class, sex);
  }

  public static com.example.templet.model.enums.Role toDomain(Role role) {
    return EnumMapperUtils.mapEnum(com.example.templet.model.enums.Role.class, role);
  }

  public static com.example.templet.model.enums.UserStatus toDomain(UserStatus userStatus) {
    return EnumMapperUtils.mapEnum(com.example.templet.model.enums.UserStatus.class, userStatus);
  }
}
