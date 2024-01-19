package co.polarpublishing.userservice.exception;

import co.polarpublishing.common.exception.ObjectAlreadyExistException;

public class UserAlreadyExistsException extends ObjectAlreadyExistException {

    private static final int CODE = 1003;

    public UserAlreadyExistsException() {
        super(CODE, "User already exists with such email address.");
    }

    public UserAlreadyExistsException(String message) {
        super(CODE, message);
    }
}
