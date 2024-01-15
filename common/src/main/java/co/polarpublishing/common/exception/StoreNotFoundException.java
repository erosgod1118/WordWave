package co.polarpublishing.common.exception;

import co.polarpublishing.common.constant.ExceptionCode;

public class StoreNotFoundException extends CustomRuntimeException {

	public StoreNotFoundException(Long storeId) {
		super(ExceptionCode.STORE_NOT_FOUND_EXCEPTION.getCode(),
				"Store with id " + storeId + " not found!");
	}

}
