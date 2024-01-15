package co.polarpublishing.common.exception;

/**
 * Exception representing the case when object already exist but was not
 * supposed to.
 *
 * @author FMRGJ
 */
public class ObjectAlreadyExistException extends CustomException {

	public ObjectAlreadyExistException(int code, String message) {
		super(code, message);
	}

}
