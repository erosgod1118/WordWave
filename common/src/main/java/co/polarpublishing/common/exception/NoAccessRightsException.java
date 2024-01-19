package co.polarpublishing.common.exception;

public class NoAccessRightsException extends CustomException {

	private static final int CODE = 1005;

	public NoAccessRightsException() {
		super(CODE, "You have no permission to do this");
	}

}
