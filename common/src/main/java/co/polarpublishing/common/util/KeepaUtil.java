package co.polarpublishing.common.util;

import org.apache.commons.lang3.StringUtils;

public class KeepaUtil {

	public static Double convertKeepaPriceToWordwavePrice(String keepaPrice) {
		if (keepaPrice == null) {
			throw new NullPointerException("Price is null!");
		}

		if (!StringUtils.isNumeric(keepaPrice)) {
			throw new IllegalArgumentException("Invalid price!");
		}

		if (keepaPrice.length() == 1) {
			keepaPrice = "00" + keepaPrice;
		}

		StringBuilder priceBuilder = new StringBuilder(keepaPrice);
		priceBuilder.insert(keepaPrice.length() - 2, ".");

		return Double.parseDouble(priceBuilder.toString());
	}

	public static Double convertKeepaRatingToWordwaveRating(String keepaRating) {
		if (keepaRating == null) {
			throw new NullPointerException("Rating is null!");
		}
		if (!StringUtils.isNumeric(keepaRating)) {
			throw new IllegalArgumentException("Invalid price!");
		}

		StringBuilder ratingBuilder = new StringBuilder(keepaRating);
		ratingBuilder.insert(keepaRating.length() - 1, ".");

		return Double.parseDouble(ratingBuilder.toString());
	}

	public static long convertToTimestamp(long timestamp) {
		return (timestamp + 21564000) * 60000;
	}

	public static long convertToKeepaTimestamp(long timestamp) {
		return (timestamp / 60000) - 21564000;
	}

}
