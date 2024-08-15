package com.example.templet.integration;

import static com.example.templet.integration.conf.MockData.UserMockData.CLIENT1_ID;
import static com.example.templet.integration.conf.MockData.UserMockData.CLIENT2_ID;
import static com.example.templet.integration.conf.MockData.UserMockData.CREATED_CLIENT_ID;
import static com.example.templet.integration.conf.MockData.UserMockData.client1;
import static com.example.templet.integration.conf.MockData.UserMockData.client2;
import static com.example.templet.integration.conf.MockData.UserMockData.clientToCreate;
import static com.example.templet.integration.conf.MockData.UserMockData.manager1;
import static com.example.templet.integration.conf.TestUtils.CLIENT1_TOKEN;
import static com.example.templet.integration.conf.TestUtils.CLIENT2_TOKEN;
import static com.example.templet.integration.conf.TestUtils.MANAGER_TOKEN;
import static com.example.templet.integration.conf.TestUtils.anAvailableRandomPort;
import static com.example.templet.integration.conf.TestUtils.assertThrowsApiException;
import static com.example.templet.integration.conf.TestUtils.setUpJwtUtils;
import static com.example.templet.integration.conf.TestUtils.setUpS3Service;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.example.templet.endpoint.rest.api.SecurityApi;
import com.example.templet.endpoint.rest.api.UserApi;
import com.example.templet.endpoint.rest.client.ApiClient;
import com.example.templet.endpoint.rest.client.ApiException;
import com.example.templet.endpoint.rest.model.User;
import com.example.templet.integration.conf.AbstractContextInitializer;
import com.example.templet.integration.conf.TestUtils;
import com.example.templet.security.JwtUtils;
import com.example.templet.template.file.S3Service;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestComponent
@ContextConfiguration(initializers = UserIT.ContextInitializer.class)
public class UserIT {

  @MockBean private S3Service s3Service;
  @MockBean private JwtUtils jwtUtils;

  private static ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, ContextInitializer.SERVER_PORT);
  }

  @BeforeEach
  void setUp() {
    setUpS3Service(s3Service);
    setUpJwtUtils(jwtUtils);
  }

  @Test
  void client_read_ok() throws ApiException {
    ApiClient client1Client = anApiClient(CLIENT1_TOKEN);
    UserApi api = new UserApi(client1Client);

    User actualUser = api.getUserById(CLIENT1_ID);
    List<User> actual = api.getUsers(1, 5, null);
    List<User> usersWithFilterName1 = api.getUsers(1, 5, "username");
    List<User> usersWithFilterName2 = api.getUsers(1, 5, "heRiLala");
    assertEquals(client1(), actualUser);
    assertEquals(3, actual.size());
    assertTrue(actual.contains(client1()));
    assertTrue(actual.contains(client2()));
    assertTrue(actual.contains(manager1()));

    assertEquals(3, usersWithFilterName1.size());

    assertEquals(1, usersWithFilterName2.size());
    assertTrue(usersWithFilterName2.contains(client2()));
  }

  @Test
  void read_user_by_id_ok() throws ApiException {
    ApiClient client = anApiClient(CLIENT1_TOKEN);
    UserApi api = new UserApi(client);

    User actual = api.getUserById(client1().getId());

    assertEquals(client1(), actual);
  }

  @Test
  void read_user_by_id_ko() {
    ApiClient client = anApiClient(MANAGER_TOKEN);
    UserApi api = new UserApi(client);
    String userId = randomUUID().toString();

    assertThrowsApiException(
        "{\"type\":\"NotFoundException\",\"message\":\"User with id " + userId + " not found\"}",
        () -> api.getUserById(userId));
  }

  @Test
  void client_write_ok() throws ApiException {
    ApiClient client1Client = anApiClient(CLIENT1_TOKEN);
    UserApi api = new UserApi(client1Client);
    SecurityApi securityApi = new SecurityApi(client1Client);

    ApiClient client2Client = anApiClient(CLIENT2_TOKEN);
    UserApi api2 = new UserApi(client2Client);

    ApiClient manager1Token = anApiClient(MANAGER_TOKEN);
    UserApi api3 = new UserApi(manager1Token);

    ApiClient noTokenClient = anApiClient(null);
    UserApi api4 = new UserApi(manager1Token);

    String newName = "new last Name";

    User actualUser = api.getUserById(CLIENT1_ID);
    List<User> actual = api.getUsers(1, 10, null);

    User actualUpdated = api2.crupdateUserById(CLIENT2_ID, client2().lastName(newName));
    List<User> actualAfterUpdate = api.getUsers(1, 10, null);

    User actualCreated = api4.crupdateUserById(CREATED_CLIENT_ID, clientToCreate());
    List<User> actualAfterCreate = api.getUsers(1, 10, null);

    assertDoesNotThrow(() -> api3.getUserById(CLIENT1_ID));
    assertDoesNotThrow(() -> api3.crupdateUserById(CLIENT2_ID, client2().lastName(newName)));

    assertEquals(client1(), actualUser);
    assertEquals(3, actual.size());
    assertTrue(actual.contains(client1()));
    assertTrue(actual.contains(client2()));
    assertTrue(actual.contains(manager1()));

    assertEquals(newName, actualUpdated.getLastName());
    assertEquals(3, actualAfterUpdate.size());

    assertEquals(clientToCreate().getFirstName(), actualCreated.getFirstName());
    assertEquals(clientToCreate().getLastName(), actualCreated.getLastName());
    assertEquals(clientToCreate().getUsername(), actualCreated.getUsername());
    assertEquals(clientToCreate().getEmail(), actualCreated.getEmail());
    assertEquals(clientToCreate().getBirthDate(), actualCreated.getBirthDate());
    assertEquals(clientToCreate().getSex(), actualCreated.getSex());
    assertEquals(clientToCreate().getStatus(), actualCreated.getStatus());
    assertEquals(clientToCreate().getPhotoUrl(), actualCreated.getPhotoUrl());
    assertEquals(clientToCreate().getProfileBannerUrl(), actualCreated.getProfileBannerUrl());
    assertEquals(4, actualAfterCreate.size());
  }

  @Test
  void other_client_update_ko() {
    ApiClient client2Client = anApiClient(CLIENT2_TOKEN);
    UserApi api = new UserApi(client2Client);

    ApiException exception =
        assertThrows(
            ApiException.class,
            () -> api.crupdateUserById(CLIENT1_ID, client1().lastName("new last name")));
    assertEquals(
        "{\"type\":\"403 FORBIDDEN\",\"message\":\"Access is denied\"}",
        exception.getResponseBody());
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailableRandomPort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
