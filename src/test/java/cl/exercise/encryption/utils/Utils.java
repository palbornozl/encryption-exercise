package cl.exercise.encryption.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import lombok.SneakyThrows;

public class Utils {

  public static <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, clazz);
  }

  public static String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

  @SneakyThrows
  public static JsonNode readFile(String fileName, ClassLoader classLoader) {
    File file = new File(classLoader.getResource("json/" + fileName + ".json").getFile());
    return new ObjectMapper().readTree(file);
  }
}
