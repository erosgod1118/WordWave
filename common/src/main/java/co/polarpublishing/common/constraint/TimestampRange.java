package co.polarpublishing.common.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = TimestampRange.TimestampRangeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TimestampRange {

  String message() default "Invalid timestamp range";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  long min();

  long max();

  TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

  public static class TimestampRangeValidator implements ConstraintValidator<TimestampRange, Long> {
    private long min;
    private long max;
    private TimeUnit timeUnit;

    @Override
    public void initialize(TimestampRange constraintAnnotation) {
      this.min = constraintAnnotation.min();
      this.max = constraintAnnotation.max();
      this.timeUnit = constraintAnnotation.timeUnit();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
      if (value == null) {
        return true;
      }

      long currentMillis = System.currentTimeMillis();
      long minMilli = currentMillis - this.timeUnit.toMillis(this.min);
      long maxMilli = currentMillis + this.timeUnit.toMillis(this.max);

      return value >= minMilli && value <= maxMilli;
    }
  }

}
