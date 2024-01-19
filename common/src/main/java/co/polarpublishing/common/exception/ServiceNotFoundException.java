package co.polarpublishing.common.exception;

public class ServiceNotFoundException extends RuntimeException {
	public ServiceNotFoundException() {
		super();
	}

	public ServiceNotFoundException(String message) {
		super(message);
	}

	public ServiceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceNotFoundException(Throwable cause) {
		super(cause);
	}

	protected ServiceNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
