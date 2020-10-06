package cl.exercise.encryption.exception.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {

  String message() default "Username con problemas, minimo 8 caracteres [a-z][A-Z][0-9][_]";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
