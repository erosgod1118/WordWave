package co.polarpublishing.common.exception;

import co.polarpublishing.common.constant.ExceptionCode;

public class PreferenceNotFoundException extends ObjectNotFoundException {

	public PreferenceNotFoundException(String message) {
		super(ExceptionCode.PREFERENCE_NOT_FOUND_EXCEPTION.getCode(), message);
	}

}
