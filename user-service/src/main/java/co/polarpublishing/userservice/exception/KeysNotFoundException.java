package co.polarpublishing.userservice.exception;

import co.polarpublishing.common.exception.ObjectNotFoundException;

public class KeysNotFoundException extends ObjectNotFoundException {

    private static final int CODE = 1001;

    public KeysNotFoundException() {
        super(CODE, "Keys with such id was not found.");
    }

    public KeysNotFoundException(String message) {
        super(CODE, message);
    }

}
