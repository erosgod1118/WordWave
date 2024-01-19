package co.polarpublishing.common.constant;

public enum BookHistory {

	AMAZON_PRICE(0, "amazonPrice"),
	NEW_PRICE(1, "newPrice"),
	USED_PRICE(2, "usedPrice"),
	BESTSELLER_RANK(3, "bestsellerRank"),
	LIST_PRICE(4, "listPrice"),
	COLLECTIBLE_PRICE(5, "collectiblePrice"),
	REFURBISHED_PRICE(6, "refurbishedPrice"),
	NEW_FBM_SHIPPING_PRICE(7, "newFbmShippingPrice"),
	LIGHTNING_DEAL_PRICE(8, "lightningDealPrice"),
	WAREHOUSE_PRICE(9, "warehousePrice"),
	NEW_FBA_PRICE(10, "newFbaPrice"),
	NEW_OFFER_COUNT(11, "newOfferCount"),
	USED_OFFER_COUNT(12, "usedOfferCount"),
	REFURBISHED_OFFER_COUNT(13, "refurbishedOfferCount"),
	COLLECTIBLE_OFFER_COUNT(14, "collectibleOfferCount"),
	EXTRA_INFO_UPDATES(15, "extraInfoUpdates"),
	RATING(16, "rating"),
	REVIEWS_COUNT(17, "reviewsCount"),
	NEW_BUY_BOX_PRICE(18, "newBuyBoxPrice"),
	USED_NEW_SHIPPING_PRICE(19, "usedNewShippingPrice"),
	USED_VERY_GOOD_SHIPPING_PRICE(20, "usedVeryGoodShippingPrice"),
	USED_GOOD_SHIPPING_PRICE(21, "usedGoodShippingPrice"),
	USED_ACCEPTABLE_SHIPPING_PRICE(22, "usedAcceptableShippingPrice"),
	COLLECTIBLE_NEW_SHIPPING_PRICE(23, "collectibleNewShippingPrice"),
	COLLECTIBLE_VERY_GOOD_SHIPPING_PRICE(24, "collectibleVeryGoodShippingPrice"),
	COLLECTIBLE_GOOD_SHIPPING_PRICE(25, "collectibleGoodShippingPrice"),
	COLLECTIBLE_ACCEPTABLE_SHIPPING_PRICE(26, "collectibleAcceptableShippingPrice"),
	REFURBISHED_SHIPPING_PRICE(27, "refurbishedShippingPrice"),
	EBAY_NEW_SHIPPING_PRICE(28, "ebayNewShippingPrice"),
	EBAY_USED_SHIPPING_PRICE(29, "ebayUsedShippingPrice"),
	TRADE_IN_PRICE(30, "tradeInPrice"),
	RENTAL_PRICE(31, "rentalPrice"),
	ESTIMATED_UNIT_ROYALTY(-1, "estimatedUnitRoyalty"),
	ESTIMATED_DAILY_ROYALTY(-1, "estimatedDailyRoyalty"),
	ESTIMATED_MONTHLY_ROYALTY(-1, "estimatedMonthlyRoyalty"),
	ESTIMATED_DAILY_SALES(-1, "estimatedDailySales"),
	ESTIMATED_MONTHLY_SALES(-1, "estimatedMonthlySales"),
	ESTIMATED_DAILY_REVENUE(-1, "estimatedDailyRevenue"),
	ESTIMATED_MONTHLY_REVENUE(-1, "estimatedMonthlyRevenue"),
	KDP_SELECT_ENROLLMENT(-1, "kdpSelectEnrollment");

	private int keepaIndex;
	private String name;

	private BookHistory(int keepaIndex, String name) {
		this.keepaIndex = keepaIndex;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getKeepaIndex() {
		return keepaIndex;
	}

}
