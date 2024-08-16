package com.example.templet.integration.conf.MockData;

import static com.example.templet.integration.conf.TestUtils.CLIENT1_BANNER_URL;
import static com.example.templet.integration.conf.TestUtils.CLIENT1_PROFILE_URL;
import static com.example.templet.integration.conf.TestUtils.CLIENT2_BANNER_URL;
import static com.example.templet.integration.conf.TestUtils.CLIENT2_PROFILE_URL;
import static com.example.templet.integration.conf.TestUtils.CLIENT2_TOKEN;

import com.example.templet.endpoint.mapper.UserMapper;
import com.example.templet.endpoint.rest.model.Role;
import com.example.templet.endpoint.rest.model.Sex;
import com.example.templet.endpoint.rest.model.SignUp;
import com.example.templet.endpoint.rest.model.User;
import com.example.templet.endpoint.rest.model.UserPicture;
import com.example.templet.endpoint.rest.model.UserPictureType;
import com.example.templet.endpoint.rest.model.UserStatus;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserMockData {
  public static final String CLIENT1_ID = "client1_id";
  public static final String CLIENT2_ID = "client2_id";
  public static final String MANAGER1_ID = "manager1_id";
  public static final String CLIENT1_PASSWORD = "client1_password";
  public static final String CLIENT2_PASSWORD = "client2_password";
  public static final String MANAGER1_PASSWORD = "manager1_password";

  public static final String CREATED_CLIENT_ID = "created_client_id";

  public static User client1() {
    return new User()
        .id(CLIENT1_ID)
        .firstName("Ryan")
        .lastName("Andria")
        .username("username_client1")
        .role(Role.MANAGER)
        .email("test@gmail.com")
        .birthDate(LocalDate.parse("1995-01-01"))
        .sex(Sex.M)
        .status(UserStatus.ENABLED)
        .photoUrl(CLIENT1_PROFILE_URL)
        .profileBannerUrl(null)
        .entranceDatetime(Instant.parse("2000-01-01T08:12:20.00Z"));
  }

  public static User client2() {
    return new User()
        .id(CLIENT2_ID)
        .firstName("Herilala")
        .lastName("Raf")
        .username("username_client2")
        .role(Role.MANAGER)
        .email("hei.hajatiana@gmail.com")
        .birthDate(LocalDate.parse("2002-01-01"))
        .sex(Sex.F)
        .status(UserStatus.ENABLED)
        .photoUrl(null)
        .profileBannerUrl(null)
        .entranceDatetime(Instant.parse("2002-01-01T08:12:20.00Z"));
  }

  public static User clientToCreate() {
    return new User()
        .id(CREATED_CLIENT_ID)
        .firstName("Amira")
        .lastName("Khan")
        .username("username_client3")
        .role(Role.MANAGER)
        .email("test+amira@hei.school")
        .birthDate(LocalDate.parse("1995-07-15"))
        .sex(Sex.M)
        .status(UserStatus.ENABLED)
        .photoUrl(null)
        .profileBannerUrl(null)
        .entranceDatetime(null);
  }

  public static SignUp signUpToCreate() {
    return new SignUp()
        .id(CREATED_CLIENT_ID)
        .lastName(clientToCreate().getLastName())
        .firstName(clientToCreate().getFirstName())
        .birthDate(clientToCreate().getBirthDate())
        .email(clientToCreate().getEmail())
        .status(UserStatus.ENABLED)
        .sex(clientToCreate().getSex())
        .role(clientToCreate().getRole())
        .password("password");
  }

  public static User manager1() {
    return new User()
        .id(MANAGER1_ID)
        .firstName("Vano")
        .lastName("Andria")
        .username("username_manager1")
        .role(Role.MANAGER)
        .email("test+vano@hei.school")
        .birthDate(LocalDate.parse("2000-01-01"))
        .sex(Sex.M)
        .status(UserStatus.ENABLED)
        .photoUrl(null)
        .profileBannerUrl(null)
        .entranceDatetime(Instant.parse("2000-09-01T08:12:20.00Z"));
  }

  public static com.example.templet.model.Whoami whoamiClient1() {
    User rest = client1();
    return com.example.templet.model.Whoami.builder()
        .user(
            com.example.templet.repository.model.User.builder()
                .id(rest.getId())
                .firstname(rest.getFirstName())
                .lastname(rest.getLastName())
                .birthdate(rest.getBirthDate())
                .lastUpdateDatetime(Instant.now())
                .mail(rest.getEmail())
                .username(rest.getUsername())
                .status(UserMapper.toDomain(rest.getStatus()))
                .sex(UserMapper.toDomain((rest.getSex())))
                .role(UserMapper.toDomain((rest.getRole())))
                .build())
        .bearer(CLIENT2_TOKEN)
        .build();
  }

  public static com.example.templet.model.Whoami whoamiClient2() {
    User rest = client2();
    return com.example.templet.model.Whoami.builder()
        .user(
            com.example.templet.repository.model.User.builder()
                .id(rest.getId())
                .firstname(rest.getFirstName())
                .lastname(rest.getLastName())
                .birthdate(rest.getBirthDate())
                .lastUpdateDatetime(Instant.now())
                .mail(rest.getEmail())
                .username(rest.getUsername())
                .status(UserMapper.toDomain(rest.getStatus()))
                .sex(UserMapper.toDomain((rest.getSex())))
                .role(UserMapper.toDomain((rest.getRole())))
                .build())
        .bearer(CLIENT2_TOKEN)
        .build();
  }

  public static com.example.templet.model.Whoami whoamiManager() {
    User rest = manager1();
    return com.example.templet.model.Whoami.builder()
        .user(
            com.example.templet.repository.model.User.builder()
                .id(rest.getId())
                .firstname(rest.getFirstName())
                .lastname(rest.getLastName())
                .birthdate(rest.getBirthDate())
                .lastUpdateDatetime(Instant.now())
                .mail(rest.getEmail())
                .username(rest.getUsername())
                .status(UserMapper.toDomain(rest.getStatus()))
                .sex(UserMapper.toDomain((rest.getSex())))
                .role(UserMapper.toDomain((rest.getRole())))
                .build())
        .bearer(CLIENT2_TOKEN)
        .build();
  }

  public static UserPicture userPictureClient1Profile() {
    return new UserPicture()
        .userId(CLIENT1_ID)
        .type(UserPictureType.PROFILE)
        .url(CLIENT1_PROFILE_URL);
  }

  public static UserPicture userPictureClient2Profile() {
    return new UserPicture()
        .userId(CLIENT2_ID)
        .type(UserPictureType.PROFILE)
        .url(CLIENT2_PROFILE_URL);
  }

  public static UserPicture userPictureClient1Banner() {
    return new UserPicture()
        .userId(CLIENT1_ID)
        .type(UserPictureType.BANNER)
        .url(CLIENT1_BANNER_URL);
  }

  public static UserPicture userPictureClient2Banner() {
    return new UserPicture()
        .userId(CLIENT2_ID)
        .type(UserPictureType.BANNER)
        .url(CLIENT2_BANNER_URL);
  }
}
