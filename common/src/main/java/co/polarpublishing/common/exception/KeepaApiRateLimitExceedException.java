package co.polarpublishing.common.exception;

import co.polarpublishing.common.constant.ExceptionCode;

public class KeepaApiRateLimitExceedException extends CustomException {

	public KeepaApiRateLimitExceedException(String message) {
		super(ExceptionCode.KEEPA_API_RATE_LIMIT_EXCEED_EXCEPTION.getCode(), message);
	}

}
