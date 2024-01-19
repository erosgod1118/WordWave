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
 * Constraint that checks if sorting property exist in specified class or not.
 *
 * @author FMRGJ
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SortingProperty.SortingPropertyValidator.class)
@Documented
public @interface SortingProperty {

	String message() default "Sorting property not found!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<?> value();

	/**
	 * Checks if sorting property existing in specified class or not.
	 */
	public static class SortingPropertyValidator implements ConstraintValidator<SortingProperty, String> {
		private Class<?> type;

		@Override
		public void initialize(SortingProperty sortingProperty) {
			this.type = sortingProperty.value();
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			// Leaving null and empty checks to other contraint validators.
			if (value == null || value.trim().isEmpty()) {
				return true;
			}

			try {
				return this.type.getDeclaredField(value.trim()) != null;
			} catch (NoSuchFieldException | SecurityException ex) {
				return false;
			}
		}
	}

}
