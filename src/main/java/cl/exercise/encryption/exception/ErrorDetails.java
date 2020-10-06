package cl.exercise.encryption.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorDetails {

  public static final String DATE_FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_YMD_HMS)
  private LocalDateTime timestamp;

  private int status;

  private String title;

  private String detail;

  private String source;

  @Override
  public String toString() {
    return "ErrorDetails{"
        + "timestamp="
        + timestamp
        + ", status="
        + status
        + ", title='"
        + title
        + '\''
        + ", detail='"
        + detail
        + '\''
        + ", source='"
        + source
        + '\''
        + '}';
  }
}
