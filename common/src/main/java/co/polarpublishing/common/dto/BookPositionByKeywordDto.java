package co.polarpublishing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookPositionByKeywordDto {

  private String asin;
  private Long keywordId;
  private Long marketPlaceId;
  private Long storeId;
  private Integer position;
  private Long timeStamp;

}
