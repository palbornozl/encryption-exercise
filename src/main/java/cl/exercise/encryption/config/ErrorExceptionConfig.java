package cl.exercise.encryption.config;

import cl.exercise.encryption.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorExceptionConfig {
  @Bean
  public GlobalExceptionHandler getExceptionHandler() {
    return new GlobalExceptionHandler();
  }
}
