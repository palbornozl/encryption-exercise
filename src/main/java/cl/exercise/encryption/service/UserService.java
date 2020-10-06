package cl.exercise.encryption.service;

import cl.exercise.encryption.dto.RutResponse;
import cl.exercise.encryption.dto.UserDTO;
import cl.exercise.encryption.encrypt.EncryptionDES;
import cl.exercise.encryption.entities.UserEntity;
import cl.exercise.encryption.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

  private final UserRepository repository;

  private final RutApiService apiService;

  private final EncryptionDES encryptionDES;

  public UserService(
      UserRepository repository, RutApiService apiService, EncryptionDES encryptionDES) {
    this.repository = repository;
    this.apiService = apiService;
    this.encryptionDES = encryptionDES;
  }

  public List<UserDTO> getAllRegisterUsers() {
    log.info("--> Getting all users...");
    List<UserDTO> userDTO = new LinkedList<>();
    repository
        .findAll()
        .forEach(
            userEntity ->
                userDTO.add(
                    UserDTO.builder()
                        .name(userEntity.getUserName())
                        .phone(userEntity.getPhone())
                        .username(userEntity.getName())
                        .userEmail(userEntity.getEmail())
                        .build()));

    return userDTO;
  }

  @SneakyThrows
  public UserDTO getUserByEmail(String email) {
    log.info("--> Getting user by email...");
    UserEntity userEntity = repository.findByEmail(email);

    return userEntity != null
        ? UserDTO.builder()
            .name(userEntity.getUserName())
            .phone(userEntity.getPhone())
            .username(userEntity.getName())
            .userEmail(userEntity.getEmail())
            .build()
        : null;
  }

  @SneakyThrows
  public String saveUser(UserDTO userDTO) {
    log.info("--> Saving user...");
    UserEntity userEntity =
        repository.save(
            UserEntity.builder()
                .name(userDTO.getName())
                .email(userDTO.getUserEmail())
                .userName(userDTO.getUsername())
                .phone(userDTO.getPhone())
                .build());

    return userEntity.getEmail();
  }

  @SneakyThrows
  public RutResponse getRutInfo(String rut) {
    log.info("--> Getting Rut info...");
    JsonNode jsonNode = apiService.callRut(encryptionDES.encrypt(rut));
    Map<String, Object> result =
        (new ObjectMapper()).convertValue(jsonNode, new TypeReference<Map<String, Object>>() {});

    JsonNode items = jsonNode.get("result").get("items");
    Map<String, Integer> registerCount = new LinkedHashMap<String, Integer>();
    registerCount.put("registerCount", Integer.valueOf(items.size()));

    return RutResponse.builder()
        .responseCode((Integer) result.get("responseCode"))
        .description(result.get("description").toString())
        .elapsedTime(Long.parseLong(result.get("elapsedTime").toString()))
        .result(registerCount)
        .build();
  }
}
