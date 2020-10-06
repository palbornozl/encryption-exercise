package cl.exercise.encryption.encrypt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EncryptionDESTest {

  @Autowired EncryptionDES encryptionDES;
  private static final String rut = "1-9";
  private static final String rutEncrypted = "FyaSTkGi8So=";

  @Test
  public void shouldEncrypt() {
    String result = encryptionDES.encrypt(rut);
    assertEquals(rutEncrypted, result);
  }
}
