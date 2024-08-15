package com.example.templet.integration.conf;

import static com.example.templet.constant.SecurityConstants.TOKEN_PREFIX;
import static com.example.templet.integration.conf.MockData.UserMockData.CLIENT1_PASSWORD;
import static com.example.templet.integration.conf.MockData.UserMockData.CLIENT2_PASSWORD;
import static com.example.templet.integration.conf.MockData.UserMockData.CREATED_CLIENT_ID;
import static com.example.templet.integration.conf.MockData.UserMockData.MANAGER1_PASSWORD;
import static com.example.templet.integration.conf.MockData.UserMockData.client1;
import static com.example.templet.integration.conf.MockData.UserMockData.client2;
import static com.example.templet.integration.conf.MockData.UserMockData.clientToCreate;
import static com.example.templet.integration.conf.MockData.UserMockData.manager1;
import static com.example.templet.integration.conf.MockData.UserMockData.whoamiClient1;
import static com.example.templet.integration.conf.MockData.UserMockData.whoamiClient2;
import static com.example.templet.integration.conf.MockData.UserMockData.whoamiManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.templet.constant.FileConstant;
import com.example.templet.endpoint.mapper.UserMapper;
import com.example.templet.endpoint.rest.client.ApiClient;
import com.example.templet.endpoint.rest.client.ApiException;
import com.example.templet.model.exception.BadRequestException;
import com.example.templet.repository.model.UserAuth;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.UserAuthService;
import com.example.templet.template.file.S3Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.function.Executable;

@AllArgsConstructor
public class TestUtils {

  public static final String BAD_TOKEN = "bad_token";
  public static final String CLIENT1_TOKEN = "client1_token";
  public static final String CLIENT2_TOKEN = "client2_token";
  public static final String MANAGER_TOKEN = "manger1_token";

  public static final String CLIENT1_PROFILE_KEY = "client1_profile_key";
  public static final String CLIENT1_BANNER_KEY = "client1_banner_key";
  public static final String CLIENT2_PROFILE_KEY = "client2_profile_key";
  public static final String CLIENT2_BANNER_KEY = "client2_banner_key";

  public static final String CLIENT1_PROFILE_URL = "https://profile.url.com/client1";
  public static final String CLIENT1_BANNER_URL = "https://banner.url.com/client1";
  public static final String CLIENT2_PROFILE_URL = "https://profile.url.com/client2";
  public static final String CLIENT2_BANNER_URL = "https://banner.url.com/client2";

  public static final String CLIENT1_PROFILE_URL_JPG = "https://profile.url.com/client1/jpg";

  public static final String POST1_PICTURE1_KEY = "post1_bucket_picture1_id_key";
  public static final String POST1_PICTURE2_KEY = "post1_bucket_picture2_id_key";
  public static final String POST1_PICTURE1_URL = "https://post1.url.com/picture1";
  public static final String POST1_PICTURE2_URL = "https://post1.url.com/picture2";

  public static final String POST1_THUMBNAIL_KEY = "post1_thumbnail_key";
  public static final String POST1_THUMBNAIL_URL = "https://post1.url.com/thumbnail";

  private static UserMapper userMapper;

  /*  public static final FirebaseUser firebaseUserClient1 =
      new FirebaseUser("test@gmail.com", "uQp7l4pzKuaaqCXjruhZw525pI23");
  public static final FirebaseUser firebaseUserClient2 =
      new FirebaseUser("hei.hajatiana@gmail.com", "W2O94puRphSI6HkaCP7kAjA9GFB2");
  public static final FirebaseUser firebaseUserManager1 =
      new FirebaseUser("test+vano@hei.school", "manager1_firebase_id");

  public static final FirebaseUser firebaseCreateClientId =
      new FirebaseUser(signUpToCreate().getEmail(), signUpToCreate().getProviderId());*/

  public static void setUpJwtUtils(JwtUtils jwtUtils) {
    when(jwtUtils.getSubjectFromJwtToken(BAD_TOKEN)).thenReturn(null);
    when(jwtUtils.getSubjectFromJwtToken(CLIENT1_TOKEN)).thenReturn(client1().getEmail());
    when(jwtUtils.getSubjectFromJwtToken(CLIENT2_TOKEN)).thenReturn(client2().getEmail());
    when(jwtUtils.getSubjectFromJwtToken(MANAGER_TOKEN)).thenReturn(manager1().getEmail());
    when(jwtUtils.getSubjectFromJwtToken(CREATED_CLIENT_ID))
        .thenReturn(clientToCreate().getEmail());
    when(jwtUtils.validateJwtToken(BAD_TOKEN)).thenReturn(false);
    when(jwtUtils.validateJwtToken(CLIENT1_TOKEN)).thenReturn(true);
    when(jwtUtils.validateJwtToken(CLIENT2_TOKEN)).thenReturn(true);
    when(jwtUtils.validateJwtToken(MANAGER_TOKEN)).thenReturn(true);
    when(jwtUtils.validateJwtToken(CREATED_CLIENT_ID)).thenReturn(true);
    when(jwtUtils.parseJwt(TOKEN_PREFIX + BAD_TOKEN)).thenReturn(BAD_TOKEN);
    when(jwtUtils.parseJwt(TOKEN_PREFIX + CLIENT1_TOKEN)).thenReturn(CLIENT1_TOKEN);
    when(jwtUtils.parseJwt(TOKEN_PREFIX + CLIENT2_TOKEN)).thenReturn(CLIENT2_TOKEN);
    when(jwtUtils.parseJwt(TOKEN_PREFIX + MANAGER_TOKEN)).thenReturn(MANAGER_TOKEN);
    when(jwtUtils.parseJwt(TOKEN_PREFIX + CREATED_CLIENT_ID)).thenReturn(CREATED_CLIENT_ID);
  }

  public static void setUpUserAuthService(UserAuthService userAuthService) {
    when(userAuthService.whoami(BAD_TOKEN)).thenReturn(null);
    when(userAuthService.whoami(CLIENT1_TOKEN)).thenReturn(whoamiClient1());
    when(userAuthService.whoami(CLIENT2_TOKEN)).thenReturn(whoamiClient2());
    when(userAuthService.whoami(MANAGER_TOKEN)).thenReturn(whoamiManager());
    //    when(userAuthService.whoami(CREATED_CLIENT_ID)).thenReturn(clientToCreate().getEmail());
    //    when(userAuthService.GetByToken(BAD_TOKEN)).thenReturn(null);
    when(userAuthService.GetByToken(CLIENT1_TOKEN))
        .thenReturn(
            UserAuth.builder()
                .id("client1_auth_id")
                .user(userMapper.toDomain(client1()))
                .password("$2a$10$15YS98M8iBdLjBqFs9UND.vM9PXElyueBZfBofBpZSnWlXKFClY62")
                .build());
    when(userAuthService.GetByToken(CLIENT2_TOKEN))
        .thenReturn(
            UserAuth.builder()
                .id("client2_auth_id")
                .user(userMapper.toDomain(client2()))
                .password("$2a$10$FXHmCbUvNnHsUk9OM/g15umW6v/4VlrmALkIEgks5RP41sWiUeBJ6")
                .build());
    when(userAuthService.GetByToken(MANAGER_TOKEN))
        .thenReturn(
            UserAuth.builder()
                .id("manager1_auth_id")
                .user(userMapper.toDomain(manager1()))
                .password("$2a$10$bijji7vf6G9INYnajSWa0O4I7o4W/H0qNcpvENOBitEZPXq.V4WHS")
                .build());
    when(userAuthService.GetToken(CLIENT1_PASSWORD, client1().getEmail()))
        .thenReturn(CLIENT1_TOKEN);
    when(userAuthService.GetToken(CLIENT2_PASSWORD, client2().getEmail()))
        .thenReturn(CLIENT2_TOKEN);
    when(userAuthService.GetToken(MANAGER1_PASSWORD, manager1().getEmail()))
        .thenReturn(MANAGER_TOKEN);

    //
    // when(userAuthService.GetByToken(CREATED_CLIENT_ID)).thenReturn(UserAuth.builder().id().user().password().build());
  }

  public static void setUpS3Service(S3Service s3Service) {
    try {
      when(s3Service.generatePresignedUrl(CLIENT1_PROFILE_KEY, FileConstant.URL_DURATION))
          .thenReturn(new URL(CLIENT1_PROFILE_URL));
      when(s3Service.generatePresignedUrl(CLIENT1_BANNER_KEY, FileConstant.URL_DURATION))
          .thenReturn(new URL(CLIENT1_BANNER_URL));
      when(s3Service.generatePresignedUrl(CLIENT2_PROFILE_KEY, FileConstant.URL_DURATION))
          .thenReturn(new URL(CLIENT2_PROFILE_URL));
      when(s3Service.generatePresignedUrl(CLIENT2_BANNER_KEY, FileConstant.URL_DURATION))
          .thenReturn(new URL(CLIENT2_BANNER_URL));
      when(s3Service.generatePresignedUrl(POST1_PICTURE1_KEY, FileConstant.URL_DURATION))
          .thenReturn(new URL(POST1_PICTURE1_URL));
      when(s3Service.generatePresignedUrl(POST1_PICTURE2_KEY, FileConstant.URL_DURATION))
          .thenReturn(new URL(POST1_PICTURE2_URL));

      when(s3Service.generatePresignedUrl(POST1_THUMBNAIL_KEY, FileConstant.URL_DURATION))
          .thenReturn(new URL(POST1_THUMBNAIL_URL));
      //      when(s3Service.uploadObjectToS3Bucket(CLIENT1_PROFILE_KEY, jpgFileContent()))
      //          .thenReturn(CLIENT1_PROFILE_URL_JPG);

    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public static ApiClient anApiClient(String token, int serverPort) {
    ApiClient client = new ApiClient();
    client.setScheme("http");
    client.setHost("localhost");
    client.setPort(serverPort);
    client.setRequestInterceptor(
        httpRequestBuilder -> httpRequestBuilder.header("Authorization", "Bearer " + token));
    return client;
  }

  public static void assertThrowsApiException(String expectedBody, Executable executable) {
    ApiException apiException = assertThrows(ApiException.class, executable);
    assertEquals(expectedBody, apiException.getResponseBody());
  }

  public static void assertThrowsBadRequestException(String expectedBody, Executable executable) {
    BadRequestException badRequestException = assertThrows(BadRequestException.class, executable);
    assertEquals(expectedBody, badRequestException.getMessage());
  }

  public static void assertThrowsForbiddenException(Executable executable) {
    ApiException apiException = assertThrows(ApiException.class, executable);
    String responseBody = apiException.getResponseBody();
    assertEquals(
        "{" + "\"type\":\"403 FORBIDDEN\"," + "\"message\":\"Access is denied\"}", responseBody);
  }

  public static int anAvailableRandomPort() {
    try {
      return new ServerSocket(0).getLocalPort();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
