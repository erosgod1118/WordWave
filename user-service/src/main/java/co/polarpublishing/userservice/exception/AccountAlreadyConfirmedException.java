package co.polarpublishing.userservice.exception;

import co.polarpublishing.common.exception.ObjectAlreadyExistException;

public class AccountAlreadyConfirmedException extends ObjectAlreadyExistException {

    private static final int CODE = 1069;

    public AccountAlreadyConfirmedException() {
        super(CODE, "Account already confirmed, please try signing in");
    }

    public AccountAlreadyConfirmedException(String message) {
        super(CODE, message);
    }
}
