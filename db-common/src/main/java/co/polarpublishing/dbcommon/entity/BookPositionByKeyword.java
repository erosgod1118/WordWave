package co.polarpublishing.dbcommon.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_position_by_keyword")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookPositionByKeyword {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "keyword_id")
  private Keyword keyword;
  private String asin;
  @JoinColumn(name = "market_place_id")
  private Long marketPlaceId;
  @JoinColumn(name = "store_id")
  private Long storeId;
  private Integer position;
  @JoinColumn(name = "time_stamp")
  private Long timeStamp;
}
