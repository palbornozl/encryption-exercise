package cl.exercise.encryption.controller;

import static cl.exercise.encryption.util.Utils.RUT_REGEX;
import static cl.exercise.encryption.util.Utils.isValidText;

import cl.exercise.encryption.dto.UserDTO;
import cl.exercise.encryption.dto.RutResponse;
import cl.exercise.encryption.service.UserService;
import java.util.List;
import javax.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/user")
@Transactional(transactionManager = "transactionManagerEncryption")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @SneakyThrows
  @GetMapping("/all")
  public ResponseEntity<List<UserDTO>> getAll() {

    List response = service.getAllRegisterUsers();

    return new ResponseEntity<>(
        response, CollectionUtils.isEmpty(response) ? HttpStatus.NO_CONTENT : HttpStatus.OK);
  }

  @SneakyThrows
  @PostMapping("/email")
  public ResponseEntity<UserDTO> findByEmail(@RequestBody UserDTO userDTO) {

    UserDTO response = service.getUserByEmail(userDTO.getUserEmail());

    return new ResponseEntity<>(response, response == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
  }

  @SneakyThrows
  @PostMapping("/save")
  public ResponseEntity<String> save(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
    if (StringUtils.isEmpty(userDTO.getUsername()) || result.hasErrors()) {
      log.error("Validation problems [save]... {}", result.getAllErrors().toString());
      throw new IllegalArgumentException(
          "[Error] " + result.getAllErrors().get(0).getDefaultMessage());
    }

    if (service.getUserByEmail(userDTO.getUserEmail()) != null) {
      throw new IllegalArgumentException("[Error] El correo ya está registrado");
    }

    String response = service.saveUser(userDTO);
    if (StringUtils.isEmpty(response))
      throw new RuntimeException("[Error] ha ocurrido en el registro...");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @SneakyThrows
  @PostMapping("/search")
  public ResponseEntity<RutResponse> findRut(@RequestParam(name = "rut") String rut) {
    if (!isValidText(rut, RUT_REGEX)) {
      log.error("Validation problems [rut]... {}");
      throw new IllegalArgumentException("[Error] Rut con problemas");
    }
    RutResponse response = service.getRutInfo(rut);
    if (StringUtils.isEmpty(response))
      throw new RuntimeException("[Error] ha ocurrido en el registro...");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
