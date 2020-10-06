package cl.exercise.encryption.service;

import static cl.exercise.encryption.utils.Utils.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cl.exercise.encryption.dto.RutResponse;
import cl.exercise.encryption.dto.UserDTO;
import cl.exercise.encryption.encrypt.EncryptionDES;
import cl.exercise.encryption.entities.UserEntity;
import cl.exercise.encryption.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest {

  UserService service;
  @Autowired EncryptionDES encryptionDES;
  @Autowired UserRepository repository;
  @Mock RutApiService mockApiService;

  File file;
  JsonNode rutResponseJson;
  private UserDTO userDTOSaveRequest;
  private UserDTO userDTOEmailRequest;
  private UserEntity userEntity;
  private RutResponse rutResponse;
  private static final String rut = "1-9";

  @Before
  public void setUp() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    JsonNode jsonNode;

    jsonNode = readFile("saveRequest", classLoader);
    userDTOSaveRequest = new ObjectMapper().readValue(jsonNode.toPrettyString(), UserDTO.class);

    jsonNode = readFile("emailRequest", classLoader);
    userDTOEmailRequest = new ObjectMapper().readValue(jsonNode.toPrettyString(), UserDTO.class);

    rutResponseJson = readFile("rutResponseAPI", classLoader);
    rutResponse =
        new ObjectMapper()
            .readValue(readFile("rutResponse", classLoader).toPrettyString(), RutResponse.class);

    service = new UserService(repository, mockApiService, encryptionDES);
  }

  @Test
  public void shouldSave() {
    String emailSaved = service.saveUser(userDTOSaveRequest);
    assertEquals(userDTOEmailRequest.getUserEmail(), emailSaved);
  }

  @Test
  public void shouldFindUserByEmail() {
    UserDTO userByEmail = service.getUserByEmail(userDTOEmailRequest.getUserEmail());
    assertEquals(userByEmail.getUserEmail(), userDTOEmailRequest.getUserEmail());
  }

  @Test
  public void shouldFindRut() {
    Mockito.when(mockApiService.callRut(encryptionDES.encrypt(rut))).thenReturn(rutResponseJson);
    RutResponse rutResponseService = service.getRutInfo(rut);
    assertEquals(
        rutResponse.getResult().get("registerCount").intValue(),
        rutResponseService.getResult().get("registerCount").intValue());
  }
}
