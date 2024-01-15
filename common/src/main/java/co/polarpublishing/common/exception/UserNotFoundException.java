package co.polarpublishing.common.exception;

import co.polarpublishing.common.constant.ExceptionCode;

public class UserNotFoundException extends CustomRuntimeException {

	public UserNotFoundException(Long userId) {
		super(ExceptionCode.USER_NOT_FOUND_EXCEPTION.getCode(),
				String.format("User with id %d not found!", userId));
	}

}
