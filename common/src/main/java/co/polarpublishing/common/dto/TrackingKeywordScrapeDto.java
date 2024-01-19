package co.polarpublishing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TrackingKeywordScrapeDto {

  private String nameOfBook;
  private String bookType;
  private String keywordName;
  private String asin;
  private Long keywordId;
  private Long marketPlaceId;
  private String marketPlaceName;
  private Long storeId;
  private Integer page;
  private Integer position;

}
