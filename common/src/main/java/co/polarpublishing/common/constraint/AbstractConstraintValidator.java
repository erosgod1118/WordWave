package co.polarpublishing.common.constraint;

import javax.validation.ConstraintValidatorContext;

/**
 * Abstract constraint validator.
 *
 * @author FMRGJ
 */
public class AbstractConstraintValidator {

	protected void buildCustomConstraintViolation(ConstraintValidatorContext context, String message) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
	}

}
