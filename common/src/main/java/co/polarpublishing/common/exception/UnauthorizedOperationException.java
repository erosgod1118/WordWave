package co.polarpublishing.common.exception;

import co.polarpublishing.common.constant.ExceptionCode;

public class UnauthorizedOperationException extends CustomException {

	public UnauthorizedOperationException(String message) {
		super(ExceptionCode.UNAUTHORIZED_OPERATION_EXCEPTION.getCode(), message);
	}

}
