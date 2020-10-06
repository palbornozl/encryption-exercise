package cl.exercise.encryption.dto;

import cl.exercise.encryption.exception.validator.UserEmailConstraint;
import cl.exercise.encryption.exception.validator.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(builderClassName = "UserDTOBuilder", toBuilder = true)
@JsonDeserialize(builder = UserDTO.UserDTOBuilder.class)
public class UserDTO {

  @JsonProperty("name")
  @JsonFormat(shape = Shape.STRING)
  @Size(min = 2, max = 250, message = "Largo no aceptado, minimo 3 y maximo 250 letras")
  private String name;

  @JsonProperty("username")
  @Size(min = 8, max = 25, message = "Largo no aceptado, minimo 8 y maximo 25 letras")
  @UsernameConstraint
  private String username;

  @JsonProperty("email")
  @Size(min = 6, max = 100, message = "Largo no aceptado, minimo 6 y maximo 100 letras")
  @UserEmailConstraint
  @NonNull
  private String userEmail;

  @JsonProperty("phone")
  @JsonFormat(shape = Shape.NUMBER_INT)
  @Min(value = 10000000)
  @Max(value = 99999999)
  private Long phone;
}
