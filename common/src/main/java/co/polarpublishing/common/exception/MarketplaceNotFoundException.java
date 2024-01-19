package co.polarpublishing.common.exception;

import co.polarpublishing.common.constant.ExceptionCode;

/**
 * Exception representing the case when marketplace is not found when it is
 * supposed to be.
 *
 * @author FMRGJ
 */
public class MarketplaceNotFoundException extends CustomRuntimeException {

	public MarketplaceNotFoundException(Long marketplaceId) {
		super(ExceptionCode.MARKETPLACE_NOT_FOUND_EXCEPTION.getCode(),
				"Marketplace with id " + marketplaceId + " not found!");
	}

	public MarketplaceNotFoundException(String marketplaceName) {
		super(ExceptionCode.MARKETPLACE_NOT_FOUND_EXCEPTION.getCode(),
				"Marketplace " + marketplaceName + " not found!");
	}

}
