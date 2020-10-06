package cl.exercise.encryption.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RutApiService {
  private final WebClient webClient;

  public RutApiService(WebClient webClient) {
    this.webClient = webClient;
  }

  @SneakyThrows
  public JsonNode callRut(String rutEncrypted) {
    MultiValueMap parameters = new LinkedMultiValueMap();
    parameters.add("rut", rutEncrypted);

    return callSearchAPI(parameters);
  }

  private JsonNode callSearchAPI(MultiValueMap parameters) {
    StopWatch watch = new StopWatch();
    log.info("Call search {}", parameters);
    watch.start();
    JsonNode response =
        this.webClient
            .get()
            .uri(uriBuilder -> uriBuilder.queryParams(parameters).build())
            .retrieve()
            .onStatus(
                HttpStatus::is4xxClientError,
                responseError -> {
                  log.error("4xx error");
                  return Mono.error(new RuntimeException("4xx"));
                })
            .onStatus(
                HttpStatus::is5xxServerError,
                responseError -> {
                  log.error("5xx error");
                  return Mono.error(new RuntimeException("5xx"));
                })
            .bodyToMono(JsonNode.class)
            .block();
    watch.stop();
    log.info("End Call search {}ms", watch.getTotalTimeMillis());

    ((ObjectNode) response).put("elapsedTime", watch.getLastTaskTimeMillis());

    return response;
  }
}
