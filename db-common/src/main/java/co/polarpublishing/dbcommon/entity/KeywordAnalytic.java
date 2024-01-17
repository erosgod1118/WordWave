package co.polarpublishing.dbcommon.entity;

import co.polarpublishing.common.constant.KeywordAnalyticMiscellaneous;
import co.polarpublishing.dbcommon.entity.attributeconverter.BothWaysJsonNodeConverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity representing computed analytics of a keyword.
 *
 * @author mani
 */
@Entity
@Table(name = "keywords_analytics")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordAnalytic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long marketplaceId;
  private Long storeId;
  @ManyToOne
  @JoinColumn(name = "keyword_id")
  private Keyword keyword;
  private Long timestamp;
  private Double averagePrice;
  @Column(name = "average_star_rating")
  private Double averageRating;
  @Column(name = "average_best_seller_rank")
  private Long averageBestsellerRank;
  @Column(name = "average_reviews")
  private Long averageReviewsQuantity;
  @Convert(converter = BothWaysJsonNodeConverter.class)
  private JsonNode miscellaneous;
  @OneToOne
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private SearchResult searchResult;
  private Long keywordResearchSessionId;

  @Override
  public String toString() {
    return "KeywordAnalytic{" +
        "id=" + id +
        ", marketplaceId=" + marketplaceId +
        ", storeId=" + storeId +
        ", keyword=" + keyword +
        ", timestamp=" + timestamp +
        ", averagePrice=" + averagePrice +
        ", averageRating=" + averageRating +
        ", averageBestsellerRank=" + averageBestsellerRank +
        ", averageReviewsQuantity=" + averageReviewsQuantity +
        ", miscellaneous=" + miscellaneous +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KeywordAnalytic that = (KeywordAnalytic) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }

  @PrePersist
  public void setTimestamp() {
    this.timestamp = System.currentTimeMillis();
  }

  public JsonNode getDataNodeFromMiscellaneousDocument(KeywordAnalyticMiscellaneous
                                                           miscellaneousDataType) {

    if (this.miscellaneous == null || this.miscellaneous.isNull() || this.miscellaneous.get
        (miscellaneousDataType.getName()) == null || this.miscellaneous.get(miscellaneousDataType
        .getName()).isNull()) {
      return NullNode.getInstance();
    }

    return this.miscellaneous.get(miscellaneousDataType.getName());
  }

  public Integer getCompetitionScore() {

    JsonNode dataNode = this.getDataNodeFromMiscellaneousDocument(KeywordAnalyticMiscellaneous
        .COMPETITION_SCORE);
    return dataNode.isNull() ? null : dataNode.asInt();
  }

  public Integer getOpportunityScore() {

    JsonNode dataNode = this.getDataNodeFromMiscellaneousDocument(KeywordAnalyticMiscellaneous
        .OPPORTUNITY_SCORE);
    return dataNode.isNull() ? null : dataNode.asInt();
  }

  public Integer getMarketSizeScore() {

    JsonNode dataNode = this.getDataNodeFromMiscellaneousDocument(KeywordAnalyticMiscellaneous
        .MARKET_SIZE_SCORE);
    return dataNode.isNull() ? null : dataNode.asInt();
  }

  public Long getAvgMinMonthlySalesQuantity() {

    JsonNode dataNode = this.getDataNodeFromMiscellaneousDocument(KeywordAnalyticMiscellaneous
        .AVERAGE_MIN_MONTHLY_SALES);
    return dataNode.isNull() ? null : dataNode.asLong();
  }

  public Long getAvgMaxMonthlySalesQuantity() {

    JsonNode dataNode = this.getDataNodeFromMiscellaneousDocument(KeywordAnalyticMiscellaneous
        .AVERAGE_MAX_MONTHLY_SALES);
    return dataNode.isNull() ? null : dataNode.asLong();
  }

  public Double getAvgMinMonthlyEarnings() {

    JsonNode dataNode = this.getDataNodeFromMiscellaneousDocument(KeywordAnalyticMiscellaneous
        .AVERAGE_MIN_MONTHLY_EARNING);
    return dataNode.isNull() ? null : dataNode.asDouble();
  }

  public Double getAvgMaxMonthlyEarnings() {

    JsonNode dataNode = this.getDataNodeFromMiscellaneousDocument(KeywordAnalyticMiscellaneous
        .AVERAGE_MAX_MONTHLY_EARNING);
    return dataNode.isNull() ? null : dataNode.asDouble();
  }

  public Long getSearchResultBooksQuantity() {

    JsonNode dataNode = this.getDataNodeFromMiscellaneousDocument(KeywordAnalyticMiscellaneous
        .SEARCH_RESULT_BOOKS_QUANTITY);
    return dataNode.isNull() ? null : dataNode.asLong();
  }

  public Long getFirstPageAvgBsr() {

    JsonNode dataNode = this.getDataNodeFromMiscellaneousDocument(KeywordAnalyticMiscellaneous
        .FIRST_PAGE_AVERAGE_BESTSELLER_RANK);
    return dataNode.isNull() ? null : dataNode.asLong();
  }
}
