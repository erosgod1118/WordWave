package co.polarpublishing.common.constant;

public enum BookMiscellaneous {

  ASIN("asin", "asin"),
  TITLE("title", "title"),
  AUTHOR("author", "author"),
  IMAGES("images", "imagesCSV"),
  PAGES_QUANTITY("numberOfPages", "numberOfPages"),
  HISTORY("history", "csv"),
  PUBLISHER("publisher", "publisher"),
  PUBLICATION_DATE("publicationDate", "publicationDate"),
  IS_COMPLETELY_UP_TO_DATE("isCompletelyUpToDate", "isCompletelyUpToDate"),
  LAST_CHECKED_ON_AMAZON_PRICE_TIME_MINUTE("lastCheckedOnAmazonPriceTimeMinute", "lastPriceChange"),
  LAST_CHECKED_ON_RATING_TIME_MINUTE("lastCheckedOnRatingTimeMinute", "lastRatingUpdate"),
  LAST_CHECKED_ON_REVIEWS_QUANTITY_TIME_MINUTE("lastCheckedOnReviewsQuantityTimeMinute", null),
  LAST_CHECKED_ON_LIST_PRICE_TIME_MINUTE("lastCheckedOnListPriceTimeMinute", null),
  LAST_CHECKED_ON_BESTSELLER_RANK_TIME_MINUTE("lastCheckedOnBestsellerRankTimeMinute", null),
  LAST_CHECKED_ON_SALES_ESTIMATE_TIME_MINUTE("lastCheckedOnSalesEstimateTimeMinute", null),
  LAST_CHECKED_ON_ROYALTY_ESTIMATE_TIME_MINUTE("lastCheckedOnRoyaltyEstimateTimeMinute", null),
  LAST_CHECKED_ON_UNIT_ROYALTY_ESTIMATE_TIME_MINUTE("lastCheckedOnUnitRoyaltyEstimateTimeMinute", null),
  LAST_CHECKED_ON_REVENUE_ESTIMATE_TIME_MINUTE("lastCheckedOnRevenueEstimateTimeMinute", null),
  LAST_CHECKED_ON_KDP_SELECT_ENROLLMENT_TIME_MINUTE("lastCheckedOnKdpSelectEnrollmentTimeMinute", null);

  private final String keepaName;
  private final String name;

  BookMiscellaneous(String name, String keepaName) {
    this.name = name;
    this.keepaName = keepaName;
  }

  public String getName() {
    return name;
  }

  public String getKeepaName() {
    return keepaName;
  }

}
