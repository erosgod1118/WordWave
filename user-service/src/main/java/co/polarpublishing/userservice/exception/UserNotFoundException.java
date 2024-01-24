package co.polarpublishing.userservice.exception;

import co.polarpublishing.common.exception.ObjectNotFoundException;

public class UserNotFoundException extends ObjectNotFoundException {

	private static final int CODE = 1000;
	
	public UserNotFoundException() {
		super(CODE, "User with such id was not found.");
	}

	public UserNotFoundException(String message) {
		super(CODE, message);
	}

}
