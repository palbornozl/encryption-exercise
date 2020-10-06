package cl.exercise.encryption.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class Utils {

  public static final String EMAIL_REGEX =
      "^(?=.{5,100}$)^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

  public static final String RUT_REGEX = "^(\\d{1,9})([-])([\\dkK])$";

  public static final String USERNAME_REGEX = "^[a-zA-Z0-9_]+$";

  @SneakyThrows
  public static boolean isValidText(String text, String regex) {
    if (StringUtils.isEmpty(text)) return false;
    try {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(text);
      return matcher.find();
    } catch (Exception e) {
      log.error("Error con el formato");
      return false;
    }
  }
}
