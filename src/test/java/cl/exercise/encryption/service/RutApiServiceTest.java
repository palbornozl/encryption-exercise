package cl.exercise.encryption.service;

import static cl.exercise.encryption.utils.Utils.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cl.exercise.encryption.encrypt.EncryptionDES;
import com.fasterxml.jackson.databind.JsonNode;
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
public class RutApiServiceTest {

  @Mock RutApiService apiService;
  @Autowired EncryptionDES encryptionDES;
  public static final String rut = "1-9";
  String rutEncrypted;
  JsonNode jsonNode;
  JsonNode jsonNodeAPI;

  @Before
  public void setUp() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    jsonNode = readFile("rutResponse", classLoader);
    jsonNodeAPI = readFile("rutResponseAPI", classLoader);
    rutEncrypted = encryptionDES.encrypt(rut);
  }

  @Test
  public void shouldCallExternalAPI() {
    Mockito.when(apiService.callRut(rutEncrypted)).thenReturn(jsonNodeAPI);
    JsonNode response = apiService.callRut(rutEncrypted);
    assertEquals(
        jsonNode.get("result").get("registerCount").intValue(),
        response.get("result").get("items").size());
  }
}
