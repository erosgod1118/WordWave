package co.polarpublishing.common.exception;

import co.polarpublishing.common.constant.ExceptionCode;

public class UserFeatureUsageLimitExceededException extends CustomException {

	public UserFeatureUsageLimitExceededException(String message) {
		super(ExceptionCode.USER_FEATURE_USAGE_LIMIT_EXCEEDED_EXCEPTION.getCode(), message);
	}

}
