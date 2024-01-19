package co.polarpublishing.common.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * Constraint that checks if given direction is valid or not.
 *
 * @author FMRGJ
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SortingDirection.SortingDirectionValidator.class)
@Documented
public @interface SortingDirection {

  String message() default "Invalid sorting direction! Must be either 'ASC' or 'DESC'.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  public static class SortingDirectionValidator implements ConstraintValidator<SortingDirection, String> {
    @Override
    public void initialize(SortingDirection a) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      return (value.equalsIgnoreCase("asc") || value.equalsIgnoreCase("desc"));
    }
  }

}
