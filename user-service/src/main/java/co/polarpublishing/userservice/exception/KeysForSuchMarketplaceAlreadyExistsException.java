package co.polarpublishing.userservice.exception;

import co.polarpublishing.common.exception.ObjectAlreadyExistException;

public class KeysForSuchMarketplaceAlreadyExistsException extends ObjectAlreadyExistException {

	private static final int CODE = 1001;

	public KeysForSuchMarketplaceAlreadyExistsException() {
		super(CODE, "Keys for such marketplace are already exists for this user.");
	}

	public KeysForSuchMarketplaceAlreadyExistsException(String message) {
		super(CODE, message);
	}

}