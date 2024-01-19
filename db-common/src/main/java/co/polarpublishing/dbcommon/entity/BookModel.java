package co.polarpublishing.dbcommon.entity;

import co.polarpublishing.common.constant.BookHistory;
import co.polarpublishing.common.constant.BookMiscellaneous;
import co.polarpublishing.common.constant.BookType;
import co.polarpublishing.common.exception.OutOfDateException;
import co.polarpublishing.common.util.DateAndTimeUtil;
import co.polarpublishing.common.util.JsonUtil;
import co.polarpublishing.common.util.KeepaUtil;
import co.polarpublishing.common.vo.BookHistoryItem;
import co.polarpublishing.dbcommon.entity.attributeconverter.BothWaysJsonNodeConverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "books")
@Builder
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookModel extends AbstractEntity {

  @EmbeddedId
  @EqualsAndHashCode.Include
  private Id id;

  private String title;
  private String isbn;
  private String author;
  private String thumbnail;

  @Column(name = "book_type")
  @Enumerated(EnumType.STRING)
  private BookType type;

  private String publisher;
  private Integer pagesQuantity;
  private Long ratings;
  private Double price;
  private Long reviews;

  @ToString.Exclude
  @Convert(converter = BothWaysJsonNodeConverter.class)
  private JsonNode miscellaneous;

  public static class ComparatorFactory {
    public static Comparator<BookModel> titleAscComparator = (BookModel o1, BookModel o2) ->
        o1.title.compareTo(o2.title);

    public static Comparator<BookModel> titleDescComparator = (BookModel o1, BookModel o2) ->
        o2.title.compareTo(o1.title);

    public static Comparator<BookModel> getComparator(String property, String sortingDirection) {
      switch (property) {
        case "title": {
          if (sortingDirection.equalsIgnoreCase("desc")) {
            return titleDescComparator;
          } else {
            return titleAscComparator;
          }
        }
        default: {
          return null;
        }
      }
    }
  }

  @Embeddable
  @Builder
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  @EqualsAndHashCode
  public static class Id implements Serializable {

    private String asin;
    private long marketplaceId;

    public Id(UserBookKeywordAssociation.Id id) {
      this.asin = id.getAsin();
      this.marketplaceId = id.getMarketplaceId();
    }

  }

  public boolean isUpToDate(long lifeSpan) {
    if (this.updationTimestamp == null && this.creationTimestamp == null) {
      throw new RuntimeException("Invalid book. Neither creation nor update timestamp available.");
    }

    if (this.updationTimestamp != null && this.updationTimestamp <= (System.currentTimeMillis() - lifeSpan)) {
      return true;
    }

    return false;
  }

  public Long getPublicationDate() {
    log.trace("Getting publication date");

    JsonNode publicationDateJson = this.miscellaneous.get(BookMiscellaneous.PUBLICATION_DATE.getName());
    if (publicationDateJson == null || publicationDateJson.isNull() || publicationDateJson.asInt() == -1) {
      return null;
    }

    String stringDate = publicationDateJson.toString();
    Date date = DateAndTimeUtil.fromKeepaDate(stringDate);
    if(date != null) {
      return date.getTime();
    }

    return null;
  }

  public Double getLatestRating(long dataLifeSpan, boolean check) throws OutOfDateException {
    log.trace("Getting latest rating.");

    JsonNode lastRatingCheckedOnTimeMinute = this.miscellaneous.get(BookMiscellaneous.LAST_CHECKED_ON_RATING_TIME_MINUTE.getName());
    if (lastRatingCheckedOnTimeMinute == null 
      || lastRatingCheckedOnTimeMinute.isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.RATING.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.RATING.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.RATING.getName()).size() == 0) 
    {
      return null;
    }

    if (check) {
      log.trace("Checking if rating is up-to-date.");

      long dataLifeSpanTimestamp = System.currentTimeMillis() - dataLifeSpan;
      if (dataLifeSpanTimestamp > KeepaUtil.convertToTimestamp(lastRatingCheckedOnTimeMinute.asLong())) {
        throw new OutOfDateException(String.format("Rating of book %s from marketplace %s is " +
                "out-of-date", this.id.asin, this.id.marketplaceId));
      }
    }

    JsonNode ratingHistory = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.RATING.getName());
    BookHistoryItem historyItem = JsonUtil.OBJECT_MAPPER.convertValue(ratingHistory.get(ratingHistory.size() - 1), BookHistoryItem.class);
    
    return Double.parseDouble(String.valueOf(historyItem.getValue()));
  }

  public Long getLatestReviewsQuantity(long dataLifeSpan, boolean check) throws OutOfDateException {
    log.trace("Getting latest reviews.");

    JsonNode lastReviewsCheckedOnTimeMinute = this.miscellaneous.get(BookMiscellaneous.LAST_CHECKED_ON_REVIEWS_QUANTITY_TIME_MINUTE.getName());
    if (lastReviewsCheckedOnTimeMinute == null 
      || lastReviewsCheckedOnTimeMinute.isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.REVIEWS_COUNT.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.REVIEWS_COUNT.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.REVIEWS_COUNT.getName()).size() == 0) 
    {
      return null;
    }

    if (check) {
      log.trace("Checking if reviews is up-to-date.");

      long dataLifeSpanTimestamp = System.currentTimeMillis() - dataLifeSpan;
      if (dataLifeSpanTimestamp > KeepaUtil.convertToTimestamp(lastReviewsCheckedOnTimeMinute.asLong())) {
        throw new OutOfDateException(String.format("Reviews of book %s from marketplace %s is " +
                "out-of-date", this.id.asin, this.id.marketplaceId));
      }
    }

    JsonNode reviewsCountHistory = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.REVIEWS_COUNT.getName());
    BookHistoryItem historyItem = JsonUtil.OBJECT_MAPPER.convertValue(reviewsCountHistory.get(reviewsCountHistory.size() - 1), BookHistoryItem.class);
    
    return Long.parseLong(String.valueOf(historyItem.getValue()));
  }

  public Long getNumOfLastReviews(long dataLifeSpan) {
    log.trace("Getting latest reviews.");

    JsonNode lastReviewsCheckedOnTimeMinute = this.miscellaneous.get(BookMiscellaneous.LAST_CHECKED_ON_REVIEWS_QUANTITY_TIME_MINUTE.getName());
    if (lastReviewsCheckedOnTimeMinute == null 
      || lastReviewsCheckedOnTimeMinute.isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.REVIEWS_COUNT.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.REVIEWS_COUNT.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.REVIEWS_COUNT.getName()).size() == 0) 
    {
      return null;
    }

    JsonNode reviewsCountHistory = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.REVIEWS_COUNT.getName());
    BookHistoryItem lastHistoryItem = JsonUtil.OBJECT_MAPPER.convertValue(reviewsCountHistory.get(reviewsCountHistory.size() - 1), BookHistoryItem.class);
    if (null == reviewsCountHistory.get(reviewsCountHistory.size() - 4)) {
      return null;
    }

    BookHistoryItem threeDaysAgoHistoryItem = JsonUtil.OBJECT_MAPPER.convertValue(reviewsCountHistory.get(reviewsCountHistory.size() - 4), BookHistoryItem.class);
    long currentNumOfReviews = Long.parseLong(String.valueOf(lastHistoryItem.getValue()));
    long threeDaysAgoNumOfReviews = Long.parseLong(String.valueOf(threeDaysAgoHistoryItem.getValue()));
    long numOfNewReviews = currentNumOfReviews - threeDaysAgoNumOfReviews;

    log.info(String.format("Current num of reviews: %d, three days ago num of reviews: %d, numOfNewReviews: %d", currentNumOfReviews, threeDaysAgoNumOfReviews, numOfNewReviews));
    
    return numOfNewReviews;
  }

  public Double getLatestAmazonNewPrice(long dataLifeSpan, boolean check) throws OutOfDateException {
    log.trace("Getting latest amazon price.");

    JsonNode lastAmazonPriceCheckedOnTimeMinute = this.miscellaneous.get(BookMiscellaneous.LAST_CHECKED_ON_AMAZON_PRICE_TIME_MINUTE.getName());
    if (lastAmazonPriceCheckedOnTimeMinute == null 
      || lastAmazonPriceCheckedOnTimeMinute.isNull()
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.AMAZON_PRICE.getName()) == null
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.AMAZON_PRICE.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.AMAZON_PRICE.getName()).size() == 0) 
    {
      return null;
    }

    if (check) {
      log.trace("Checking if amazon price is up-to-date.");

      long dataLifeSpanTimestamp = System.currentTimeMillis() - dataLifeSpan;
      if (dataLifeSpanTimestamp > KeepaUtil.convertToTimestamp(lastAmazonPriceCheckedOnTimeMinute.asLong())) {
        throw new OutOfDateException(String.format("Amazon price of book %s from marketplace %s is " +
                "out-of-date", this.id.asin, this.id.marketplaceId));
      }
    }

    JsonNode amazonNewPriceHistory = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.AMAZON_PRICE.getName());
    BookHistoryItem historyItem = JsonUtil.OBJECT_MAPPER.convertValue(amazonNewPriceHistory.get(amazonNewPriceHistory.size() - 1), BookHistoryItem.class);
    
    return Double.parseDouble(String.valueOf(historyItem.getValue()));
  }

  public Integer getLatestBsr(long dataLifeSpan, boolean check) throws OutOfDateException {
    return getLatestBsr(dataLifeSpan, 0, check);
  }

  public Integer getLatestBsr(long dataLifeSpan, int skip, boolean check) throws OutOfDateException {
    log.trace("Getting latest bsr.");

    JsonNode lastBsrCheckedOnTimeMinute = this.miscellaneous.get(BookMiscellaneous.LAST_CHECKED_ON_BESTSELLER_RANK_TIME_MINUTE.getName());
    if (lastBsrCheckedOnTimeMinute == null 
      || lastBsrCheckedOnTimeMinute.isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.BESTSELLER_RANK.getName()) == null
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.BESTSELLER_RANK.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.BESTSELLER_RANK.getName()).size() == 0) 
    {
      return null;
    }

    if (check) {
      log.trace("Checking if bsr is up-to-date.");

      long dataLifeSpanTimestamp = System.currentTimeMillis() - dataLifeSpan;
      if (dataLifeSpanTimestamp > KeepaUtil.convertToTimestamp(lastBsrCheckedOnTimeMinute.asLong())) {
        throw new OutOfDateException(String.format("Bestseller rank of book %s from marketplace %s " +
                "is " +
                "out-of-date", this.id.asin, this.id.marketplaceId));
      }
    }

    JsonNode bsrHistory = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.BESTSELLER_RANK.getName());
    BookHistoryItem historyItem = JsonUtil.OBJECT_MAPPER.convertValue(bsrHistory.get(bsrHistory.size() - (1 + skip)), BookHistoryItem.class);
    
    return Integer.parseInt(String.valueOf(historyItem.getValue()));
  }

  public String getDailySalesEstimate(long dataLifeSpan, boolean check) throws OutOfDateException {
    log.trace("Getting latest daily sales estimate.");

    JsonNode lastSalesCheckedOnTimeMinute = this.miscellaneous.get(BookMiscellaneous.LAST_CHECKED_ON_SALES_ESTIMATE_TIME_MINUTE.getName());
    if (lastSalesCheckedOnTimeMinute == null 
      || lastSalesCheckedOnTimeMinute.isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.ESTIMATED_DAILY_SALES.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.ESTIMATED_DAILY_SALES.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.ESTIMATED_DAILY_SALES.getName()).size() == 0) 
    {
      return null;
    }

    if (check) {
      log.trace("Checking if daily sales is up-to-date.");

      long dataLifeSpanTimestamp = System.currentTimeMillis() - dataLifeSpan;
      if (dataLifeSpanTimestamp > KeepaUtil.convertToTimestamp(lastSalesCheckedOnTimeMinute.asLong())) {
        throw new OutOfDateException(String.format("Last sales estimate of book %s from marketplace" +
                " %s " +
                "is " +
                "out-of-date", this.id.asin, this.id.marketplaceId));
      }
    }

    JsonNode dailySalesHistory = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.ESTIMATED_DAILY_SALES.getName());
    BookHistoryItem historyItem = JsonUtil.OBJECT_MAPPER.convertValue(dailySalesHistory.get(dailySalesHistory.size() - 1), BookHistoryItem.class);
    
    return String.valueOf(historyItem.getValue());
  }

  public String getMonthlySalesEstimate(long dataLifeSpan, boolean check) throws OutOfDateException {
    log.trace("Getting latest monthly sales estimate.");

    JsonNode lastSalesCheckedOnTimeMinute = this.miscellaneous.get(BookMiscellaneous.LAST_CHECKED_ON_SALES_ESTIMATE_TIME_MINUTE.getName());
    if (lastSalesCheckedOnTimeMinute == null 
      || lastSalesCheckedOnTimeMinute.isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.ESTIMATED_MONTHLY_SALES.getName()) == null 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.ESTIMATED_MONTHLY_SALES.getName()).isNull() 
      || this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.ESTIMATED_MONTHLY_SALES.getName()).size() == 0) 
    {
      return null;
    }

    if (check) {
      log.trace("Checking if daily sales is up-to-date.");

      long dataLifeSpanTimestamp = System.currentTimeMillis() - dataLifeSpan;
      if (dataLifeSpanTimestamp > KeepaUtil.convertToTimestamp(lastSalesCheckedOnTimeMinute.asLong())) {
        throw new OutOfDateException(String.format("Last sales estimate of book %s from marketplace" +
                " %s " +
                "is " +
                "out-of-date", this.id.asin, this.id.marketplaceId));
      }
    }

    JsonNode monthlySalesHistory = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName()).get(BookHistory.ESTIMATED_MONTHLY_SALES.getName());
    BookHistoryItem historyItem = JsonUtil.OBJECT_MAPPER.convertValue(monthlySalesHistory.get(monthlySalesHistory.size() - 1), BookHistoryItem.class);
    
    return String.valueOf(historyItem.getValue());
  }

  public Integer getBsr(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode bsrHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.BESTSELLER_RANK.getName());
    if (bsrHistoryDocument == null || bsrHistoryDocument.isNull()) {
      return null;
    }

    int index = bsrHistoryDocument.size() - 1;
    Integer bsr = null;
    while (index >= 0) {
      BookHistoryItem bsrItem = JsonUtil.OBJECT_MAPPER.convertValue(bsrHistoryDocument.get(index), BookHistoryItem.class);
      if (bsrItem.getKeywordResearchSessionId() != null && bsrItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (bsrItem.getValue() != null) {
          String bsrValue = String.valueOf(bsrItem.getValue());
          if (bsrValue != null && !bsrValue.trim().isEmpty()) {
            bsr = Integer.parseInt(bsrValue.trim());
          }
        }

        break;
      }

      --index;
    }

    return bsr;
  }

  public Double getRating(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode ratingHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.RATING.getName());
    if (ratingHistoryDocument == null || ratingHistoryDocument.isNull()) {
      return null;
    }

    int index = ratingHistoryDocument.size() - 1;
    Double rating = null;
    while (index >= 0) {
      BookHistoryItem ratingItem = JsonUtil.OBJECT_MAPPER.convertValue(ratingHistoryDocument.get(index), BookHistoryItem.class);
      if (ratingItem.getKeywordResearchSessionId() != null && ratingItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (ratingItem.getValue() != null) {
          String ratingValue = String.valueOf(ratingItem.getValue());
          if (ratingValue != null && !ratingValue.trim().isEmpty()) {
            rating = Double.parseDouble(ratingValue.trim());
          }
        }

        break;
      }

      --index;
    }

    return rating;
  }

  public Long getReviews(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode reviewsHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.REVIEWS_COUNT.getName());
    if (reviewsHistoryDocument == null || reviewsHistoryDocument.isNull()) {
      return null;
    }

    int index = reviewsHistoryDocument.size() - 1;
    Long reviews = null;
    while (index >= 0) {
      BookHistoryItem reviewsItem = JsonUtil.OBJECT_MAPPER.convertValue(reviewsHistoryDocument.get(index), BookHistoryItem.class);
      if (reviewsItem.getKeywordResearchSessionId() != null && reviewsItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (reviewsItem.getValue() != null) {
          String reviewsValue = String.valueOf(reviewsItem.getValue());
          if (reviewsValue != null && !reviewsValue.trim().isEmpty()) {
            reviews = Long.parseLong(reviewsValue.trim());
          }
        }

        break;
      }

      --index;
    }

    return reviews;
  }

  public Double getAmazonPrice(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode amazonPriceHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.AMAZON_PRICE.getName());
    if (amazonPriceHistoryDocument == null || amazonPriceHistoryDocument.isNull()) {
      return null;
    }

    int index = amazonPriceHistoryDocument.size() - 1;
    Double amazonPrice = null;
    while (index >= 0) {
      BookHistoryItem amazonPriceItem = JsonUtil.OBJECT_MAPPER.convertValue(amazonPriceHistoryDocument.get(index), BookHistoryItem.class);
      if (amazonPriceItem.getKeywordResearchSessionId() != null && amazonPriceItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (amazonPriceItem.getValue() != null) {
          String amazonPriceValue = String.valueOf(amazonPriceItem.getValue());
          if (amazonPriceValue != null && !amazonPriceValue.trim().isEmpty()) {
            amazonPrice = Double.parseDouble(amazonPriceValue.trim());
          }
        }

        break;
      }

      --index;
    }

    return amazonPrice;
  }

  public Double getListPrice(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode listPriceHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.LIST_PRICE.getName());
    if (listPriceHistoryDocument == null || listPriceHistoryDocument.isNull()) {
      return null;
    }

    int index = listPriceHistoryDocument.size() - 1;
    Double listPrice = null;
    while (index >= 0) {
      BookHistoryItem listPriceItem = JsonUtil.OBJECT_MAPPER.convertValue(listPriceHistoryDocument.get(index), BookHistoryItem.class);
      if (listPriceItem.getKeywordResearchSessionId() != null && listPriceItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (listPriceItem.getValue() != null) {
          String listPriceValue = String.valueOf(listPriceItem.getValue());
          if (listPriceValue != null && !listPriceValue.trim().isEmpty()) {
            listPrice = Double.parseDouble(listPriceValue.trim());
          }
        }

        break;
      }

      --index;
    }

    return listPrice;
  }

  public String getUnitRoyaltyEstimate(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode unitRoyaltyEstimateHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.ESTIMATED_UNIT_ROYALTY.getName());
    if (unitRoyaltyEstimateHistoryDocument == null || unitRoyaltyEstimateHistoryDocument.isNull()) {
      return null;
    }

    int index = unitRoyaltyEstimateHistoryDocument.size() - 1;
    String unitRoyaltyEstimate = null;
    while (index >= 0) {
      BookHistoryItem unitRoyaltyEstimateItem = JsonUtil.OBJECT_MAPPER.convertValue(unitRoyaltyEstimateHistoryDocument.get(index), BookHistoryItem.class);
      if (unitRoyaltyEstimateItem.getKeywordResearchSessionId() != null && unitRoyaltyEstimateItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (unitRoyaltyEstimateItem.getValue() != null) {
          unitRoyaltyEstimate = String.valueOf(unitRoyaltyEstimateItem.getValue());
        }

        break;
      }

      --index;
    }

    return unitRoyaltyEstimate;
  }

  public String getDailyRoyaltyEstimate(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode dailyRoyaltyEstimateHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.ESTIMATED_DAILY_ROYALTY.getName());
    if (dailyRoyaltyEstimateHistoryDocument == null || dailyRoyaltyEstimateHistoryDocument.isNull()) {
      return null;
    }

    int index = dailyRoyaltyEstimateHistoryDocument.size() - 1;
    String dailyRoyaltyEstimate = null;
    while (index >= 0) {
      BookHistoryItem dailyRoyaltyEstimateItem = JsonUtil.OBJECT_MAPPER.convertValue(dailyRoyaltyEstimateHistoryDocument.get(index), BookHistoryItem.class);
      if (dailyRoyaltyEstimateItem.getKeywordResearchSessionId() != null && dailyRoyaltyEstimateItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (dailyRoyaltyEstimateItem.getValue() != null) {
          dailyRoyaltyEstimate = String.valueOf(dailyRoyaltyEstimateItem.getValue());
        }

        break;
      }

      --index;
    }

    return dailyRoyaltyEstimate;
  }

  public String getMonthlyRoyaltyEstimate(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode monthlyRoyaltyEstimateHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.ESTIMATED_MONTHLY_ROYALTY.getName());
    if (monthlyRoyaltyEstimateHistoryDocument == null || monthlyRoyaltyEstimateHistoryDocument.isNull()) {
      return null;
    }

    int index = monthlyRoyaltyEstimateHistoryDocument.size() - 1;
    String monthlyRoyaltyEstimate = null;
    while (index >= 0) {
      BookHistoryItem monthlyRoyaltyEstimateItem = JsonUtil.OBJECT_MAPPER.convertValue(monthlyRoyaltyEstimateHistoryDocument.get(index), BookHistoryItem.class);
      if (monthlyRoyaltyEstimateItem.getKeywordResearchSessionId() != null && monthlyRoyaltyEstimateItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (monthlyRoyaltyEstimateItem.getValue() != null) {
          monthlyRoyaltyEstimate = String.valueOf(monthlyRoyaltyEstimateItem.getValue());
        }

        break;
      }

      --index;
    }

    return monthlyRoyaltyEstimate;
  }

  public String getMonthlyRevenueEstimate(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode monthlyRevenueEstimateHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.ESTIMATED_MONTHLY_REVENUE.getName());
    if (monthlyRevenueEstimateHistoryDocument == null || monthlyRevenueEstimateHistoryDocument.isNull()) {
      return null;
    }

    int index = monthlyRevenueEstimateHistoryDocument.size() - 1;
    String monthlyRevenueEstimate = null;
    while (index >= 0) {
      BookHistoryItem monthlyRevenueEstimateItem = JsonUtil.OBJECT_MAPPER.convertValue(monthlyRevenueEstimateHistoryDocument.get(index), BookHistoryItem.class);
      if (monthlyRevenueEstimateItem.getKeywordResearchSessionId() != null && monthlyRevenueEstimateItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (monthlyRevenueEstimateItem.getValue() != null) {
          monthlyRevenueEstimate = String.valueOf(monthlyRevenueEstimateItem.getValue());
        }

        break;
      }

      --index;
    }

    return monthlyRevenueEstimate;
  }

  public String getDailyRevenueEstimate(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode dailyRevenueEstimateHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.ESTIMATED_DAILY_REVENUE.getName());
    if (dailyRevenueEstimateHistoryDocument == null || dailyRevenueEstimateHistoryDocument.isNull()) {
      return null;
    }

    int index = dailyRevenueEstimateHistoryDocument.size() - 1;
    String dailyRevenueEstimate = null;
    while (index >= 0) {
      BookHistoryItem dailyRevenueEstimateItem = JsonUtil.OBJECT_MAPPER.convertValue(dailyRevenueEstimateHistoryDocument.get(index), BookHistoryItem.class);
      if (dailyRevenueEstimateItem.getKeywordResearchSessionId() != null && dailyRevenueEstimateItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (dailyRevenueEstimateItem.getValue() != null) {
          dailyRevenueEstimate = String.valueOf(dailyRevenueEstimateItem.getValue());
        }

        break;
      }

      --index;
    }

    return dailyRevenueEstimate;
  }

  public String getDailySalesEstimate(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode dailySalesEstimateHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.ESTIMATED_DAILY_SALES.getName());
    if (dailySalesEstimateHistoryDocument == null || dailySalesEstimateHistoryDocument.isNull()) {
      return null;
    }

    int index = dailySalesEstimateHistoryDocument.size() - 1;
    String dailySalesEstimate = null;
    while (index >= 0) {
      BookHistoryItem dailySalesEstimateItem = JsonUtil.OBJECT_MAPPER.convertValue(dailySalesEstimateHistoryDocument.get(index), BookHistoryItem.class);
      if (dailySalesEstimateItem.getKeywordResearchSessionId() != null && dailySalesEstimateItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (dailySalesEstimateItem.getValue() != null) {
          dailySalesEstimate = String.valueOf(dailySalesEstimateItem.getValue());
        }

        break;
      }

      --index;
    }

    return dailySalesEstimate;
  }

  public Boolean getKdpSelectEnrollment(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode kdpSelectEnrollmentHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.KDP_SELECT_ENROLLMENT.getName());
    if (kdpSelectEnrollmentHistoryDocument == null || kdpSelectEnrollmentHistoryDocument.isNull()) {
      return null;
    }

    int index = kdpSelectEnrollmentHistoryDocument.size() - 1;
    Boolean kdpSelectEnrollment = null;
    while (index >= 0) {
      BookHistoryItem kdpSelectEnrollmentItem = JsonUtil.OBJECT_MAPPER.convertValue(kdpSelectEnrollmentHistoryDocument.get(index), BookHistoryItem.class);
      if (kdpSelectEnrollmentItem.getKeywordResearchSessionId() != null && kdpSelectEnrollmentItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (kdpSelectEnrollmentItem.getValue() != null) {
          kdpSelectEnrollment = Boolean.parseBoolean(String.valueOf(kdpSelectEnrollmentItem.getValue()));
        }

        break;
      }

      --index;
    }

    return kdpSelectEnrollment;
  }

  public String getMonthlySalesEstimate(Long keywordResearchSessionId) {
    if (miscellaneous == null || miscellaneous.isNull()) {
      return null;
    }

    JsonNode historyDocument = this.miscellaneous.get(BookMiscellaneous.HISTORY.getName());
    if (historyDocument == null || historyDocument.isNull()) {
      return null;
    }

    ArrayNode monthlySalesEstimateHistoryDocument = (ArrayNode) historyDocument.get(BookHistory.ESTIMATED_MONTHLY_SALES.getName());
    if (monthlySalesEstimateHistoryDocument == null || monthlySalesEstimateHistoryDocument.isNull()) {
      return null;
    }

    int index = monthlySalesEstimateHistoryDocument.size() - 1;
    String monthlySalesEstimate = null;
    while (index >= 0) {
      BookHistoryItem monthlySalesEstimateHistoryItem = JsonUtil.OBJECT_MAPPER.convertValue(monthlySalesEstimateHistoryDocument.get(index), BookHistoryItem.class);
      if (monthlySalesEstimateHistoryItem.getKeywordResearchSessionId() != null && monthlySalesEstimateHistoryItem.getKeywordResearchSessionId().equals(keywordResearchSessionId)) {
        if (monthlySalesEstimateHistoryItem.getValue() != null) {
          monthlySalesEstimate = String.valueOf(monthlySalesEstimateHistoryItem.getValue());
        }

        break;
      }

      --index;
    }

    return monthlySalesEstimate;
  }

}