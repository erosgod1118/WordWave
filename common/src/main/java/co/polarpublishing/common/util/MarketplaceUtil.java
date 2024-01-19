package co.polarpublishing.common.util;

import co.polarpublishing.common.model.URL;

public class MarketplaceUtil {

	public static String formalizeMarketplaceName(String marketplaceName) {
		if (marketplaceName == null || marketplaceName.trim().isEmpty()) {
			throw new IllegalArgumentException("Marketplace name can't be null/empty.");
		}

		marketplaceName = marketplaceName.trim();
		if (marketplaceName.charAt(0) == '.') {
			marketplaceName = marketplaceName.substring(1);
		}

		if (URL.PUBLIC_SUFFIXES.contains(marketplaceName)) {
			return "." + marketplaceName;
		}

		URL url = new URL(marketplaceName);
		if (url.getDomainName().equalsIgnoreCase("amazon")) {
			return "." + url.getPublicSuffix();
		}

		return url.getDomainName() + "." + url.getPublicSuffix();
	}

}
