package cl.exercise.encryption.exception.validator;

import static cl.exercise.encryption.util.Utils.EMAIL_REGEX;
import static cl.exercise.encryption.util.Utils.isValidText;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEmailValidator implements ConstraintValidator<UserEmailConstraint, String> {

  @Override
  public void initialize(UserEmailConstraint userEmailConstraint) {}

  @Override
  @SneakyThrows
  public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
    return isValidText(email, EMAIL_REGEX);
  }
}
