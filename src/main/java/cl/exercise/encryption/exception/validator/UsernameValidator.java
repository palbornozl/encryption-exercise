package cl.exercise.encryption.exception.validator;

import static cl.exercise.encryption.util.Utils.USERNAME_REGEX;
import static cl.exercise.encryption.util.Utils.isValidText;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

  @Override
  public void initialize(UsernameConstraint usernameConstraint) {}

  @Override
  @SneakyThrows
  public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
    return isValidText(username, USERNAME_REGEX);
  }
}
