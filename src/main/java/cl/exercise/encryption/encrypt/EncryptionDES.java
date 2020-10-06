package cl.exercise.encryption.encrypt;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EncryptionDES {

  private static final String SUPER_PASSWORD = "ionix123456";

  @SneakyThrows
  private byte[] init(int cipherMode, String text) {
    DESKeySpec dks = new DESKeySpec(SUPER_PASSWORD.getBytes("UTF8"));
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = keyFactory.generateSecret(dks);
    Cipher cipher = Cipher.getInstance("DES");
    cipher.init(cipherMode, secretKey);
    return cipher.doFinal(text.getBytes("UTF8"));
  }

  @SneakyThrows
  public String encrypt(String plainRut) {
    log.info("Encrypting rut...");
    byte[] encryptedRut = Base64.getEncoder().encode(init(Cipher.ENCRYPT_MODE, plainRut));
    return new String(encryptedRut, "UTF8");
  }
}
