package co.polarpublishing.common.exception;

import co.polarpublishing.common.constant.ExceptionCode;

public class InvalidAsinException extends CustomException {

	public InvalidAsinException(String message) {
		super(ExceptionCode.INVALID_ASIN_EXCEPTION.getCode(), message);
	}

}
