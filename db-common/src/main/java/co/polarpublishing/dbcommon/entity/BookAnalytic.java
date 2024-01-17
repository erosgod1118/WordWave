package co.polarpublishing.dbcommon.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import co.polarpublishing.dbcommon.entity.attributeconverter.BothWaysJsonNodeConverter;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing computed analytics of a book.
 *
 * @author FMRGJ
 */
@Entity
@Table(name = "books_analytics")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookAnalytic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "star_rating")
  private Double rating;

  @Column(name = "reviews")
  private Long reviewsQuantity;

  @Column(name = "best_seller_rank")
  private Long bestsellerRank;

  @Column(name = "daily_sales_min")
  private Long dailyMinSalesQuantity;

  @Column(name = "daily_sales_max")
  private Long dailyMaxSalesQuantiy;

  @Convert(converter = JsonNodeConverter.class)
  private JsonNode prices;

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "asin"),
    @JoinColumn(name = "marketplace_id")
  })
  private BookModel book;

  private Long timestamp;
  
  @Convert(converter = BothWaysJsonNodeConverter.class)
  private JsonNode miscellaneous;

  @PrePersist
  public void setTimestamp() {
    this.timestamp = System.currentTimeMillis();
  }

}
