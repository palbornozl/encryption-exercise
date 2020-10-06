package cl.exercise.encryption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(builderClassName = "UserResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = RutResponse.UserResponseBuilder.class)
public class RutResponse {

  @JsonProperty("responseCode")
  private Integer responseCode;

  @JsonProperty("description")
  private String description;

  @JsonProperty("elapsedTime")
  private Long elapsedTime;

  @JsonProperty("result")
  private Map<String, Integer> result;
}
