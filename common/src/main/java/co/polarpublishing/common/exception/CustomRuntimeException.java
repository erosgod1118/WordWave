package co.polarpublishing.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomRuntimeException extends RuntimeException {

	private int code;

	public CustomRuntimeException(int code, String message) {
		super(message);
		this.code = code;
	}

}
