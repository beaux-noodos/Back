package com.example.templet.integration;

import static com.example.templet.integration.conf.MockData.UserMockData.CLIENT1_ID;
import static com.example.templet.integration.conf.MockData.UserMockData.CLIENT1_PASSWORD;
import static com.example.templet.integration.conf.MockData.UserMockData.client1;
import static com.example.templet.integration.conf.TestUtils.BAD_TOKEN;
import static com.example.templet.integration.conf.TestUtils.CLIENT1_TOKEN;
import static com.example.templet.integration.conf.TestUtils.anAvailableRandomPort;
import static com.example.templet.integration.conf.TestUtils.assertThrowsForbiddenException;
import static com.example.templet.integration.conf.TestUtils.setUpJwtUtils;
import static com.example.templet.integration.conf.TestUtils.setUpS3Service;
import static com.example.templet.integration.conf.TestUtils.setUpUserAuthService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.example.templet.endpoint.rest.api.SecurityApi;
import com.example.templet.endpoint.rest.client.ApiClient;
import com.example.templet.endpoint.rest.client.ApiException;
import com.example.templet.endpoint.rest.model.AuthenticationPayload;
import com.example.templet.endpoint.rest.model.Whoami;
import com.example.templet.integration.conf.AbstractContextInitializer;
import com.example.templet.integration.conf.TestUtils;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.UserAuthService;
import com.example.templet.template.file.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(initializers = SecurityIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class SecurityIT {

  @MockBean private S3Service s3Service;
  @MockBean private JwtUtils jwtUtils;
  @MockBean private UserAuthService userAuthService;

  private static ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, ContextInitializer.SERVER_PORT);
  }

  @BeforeEach
  void setUp() {
    setUpS3Service(s3Service);
    setUpJwtUtils(jwtUtils);
    setUpUserAuthService(userAuthService);
  }

  @Test
  void signin_ok() throws ApiException {
    ApiClient client = anApiClient(BAD_TOKEN);
    SecurityApi api = new SecurityApi(client);

    Whoami actual =
        api.signIn(
            new AuthenticationPayload()
                .email(client1().getEmail())
                .password(CLIENT1_PASSWORD)
                );

    assertNotNull(actual);
    assertEquals(CLIENT1_ID, (actual.getUser()).getId());
    assertEquals(client1().getEmail(), (actual.getUser()).getEmail());
    assertFalse((actual.getBearer()).isEmpty());
  }

  @Test
  void signin_ko() {
    ApiClient client = anApiClient(BAD_TOKEN);
    SecurityApi api = new SecurityApi(client);

    ApiException exception1 =
        assertThrows(
            ApiException.class,
            () ->
                api.signIn(
                    new AuthenticationPayload().email("dump").password("dump")));
    assertEquals(
        "{\"type\":\"BadRequestException\",\"message\":\"Invalid credentials\"}",
        exception1.getMessage());
    assertEquals(400, exception1.getCode());

    //    assertTrue(exception1.getMessage().contains("status\":403,\"error\":\"Forbidden"));
  }

  @Test
  void whoami_ok() throws ApiException {

    ApiClient client = anApiClient(CLIENT1_TOKEN);
    SecurityApi api = new SecurityApi(client);

    Whoami actual = api.whoami();

    assertNotNull(actual);
    assertEquals(CLIENT1_ID, (actual.getUser()).getId());
    assertEquals(client1().getEmail(), (actual.getUser()).getEmail());
    assertFalse((actual.getBearer()).isEmpty());
  }

  @Test
  void whoami_ko() {
    ApiClient client = anApiClient(BAD_TOKEN);
    SecurityApi api = new SecurityApi(client);

    assertThrowsForbiddenException(api::whoami);
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailableRandomPort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
