package co.polarpublishing.common.exception;

import lombok.NoArgsConstructor;

/**
 * Thrown when object already exist which is not supposed to.
 *
 * @author FMRGJ
 */
@NoArgsConstructor
public class ObjectExistException extends Exception {

	public ObjectExistException(String message) {
		super(message);
	}

}
