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
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

/**
 * Constraint annotation that checks the validity of date and time ranges in
 * argument.
 *
 * @author FMRGJ
 */
@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateAndTimeRangeParameter.DateAndTimeRangeParameterValidator.class)
@Documented
public @interface DateAndTimeRangeParameter {

	String message() default "Invalid date and time range parameters.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int fromIndex() default 0;

	int toIndex() default 1;

	/**
	 * Date and time range validator. If to date is before from date, then it is
	 * considered invalid.
	 */
	@SupportedValidationTarget(ValidationTarget.PARAMETERS)
	public static class DateAndTimeRangeParameterValidator extends AbstractConstraintValidator
			implements ConstraintValidator<DateAndTimeRangeParameter, Object[]> {
		private int fromIndex;
		private int toIndex;

		@Override
		public void initialize(DateAndTimeRangeParameter dateAndTimeRangeParameter) {
			this.fromIndex = dateAndTimeRangeParameter.fromIndex();
			this.toIndex = dateAndTimeRangeParameter.toIndex();
		}

		@Override
		public boolean isValid(Object[] values, ConstraintValidatorContext context) {
			// Leaving null checks to other validators.
			if (values[this.fromIndex] == null && values[this.toIndex] == null) {
				return true;
			}

			if (values[this.fromIndex] == null || values[this.toIndex] == null) {
				this.buildCustomConstraintViolation(context, "FROM & TO dates must be given to form a date range.");
				return false;
			}

			if ((long) values[this.toIndex] >= (long) values[this.fromIndex]) {
				return true;
			} else {
				this.buildCustomConstraintViolation(context, "FROM date must preceed/be equal to TO date.");
				return false;
			}
		}
	}

}
