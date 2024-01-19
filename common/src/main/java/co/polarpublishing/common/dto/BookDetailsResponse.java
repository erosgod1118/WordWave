package co.polarpublishing.common.dto;

import co.polarpublishing.common.constant.BookType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookDetailsResponse implements Comparable<BookDetailsResponse> {

  @EqualsAndHashCode.Include
  private String asin;
  private String country;
  private String title;
  private String author;
  private String imageUrl;

  private Double stars;
  private Double price;

  private Long reviews;

  private Integer bsr;

  private BookType bookType;

  private int numberOfPages;

  private String publisher;
  private String url;
  private String bsrCategory;

  private Integer publicationDate;

  private Map<String, Object> miscellaneous;

  private Boolean isEnrolledInKdpSelect;
  private Boolean isSponsored;

  private KeepaCategoryTreeEntry[] categoryTree;
  private Long reviewsQty;
  private RankingStatistics rankingStatistics;
  private DescriptionStatistics descriptionStatistics;
  private APlusContentStatistics aPlusContentStatistics;

  @JsonIgnore
  private APlusContent aPlusContent;

  @JsonIgnore
  private List<RankedCategory> subRankedCategories;

  @Override
  public int compareTo(BookDetailsResponse bookDetailsResponse) {
    Integer first = Integer.parseInt(this.bsr.toString());
    Integer second = Integer.parseInt(bookDetailsResponse.getBsr().toString());
    return first.compareTo(second);
  }

  @Builder
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  @EqualsAndHashCode
  public static class DescriptionStatistics {
    private int charsQty;
    private int headingTextQty;
    private int italicTextQty;
    private int boldTextQty;
    private int underlinedTextQty;
    private int listQty;
    private int styledTextQty;
  }

  @Builder
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  @EqualsAndHashCode
  public static class APlusContentStatistics {
    private int blocksQty;
    private Integer imgsAltTextMinCharQty;
    private Integer imgsAltTextMaxCharQty;
  }

  @Builder
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  @EqualsAndHashCode
  public static class RankingStatistics {
    private int rankingCategoryTreesQty;
    private int rankingCategoriesQty;
    private Integer totalRankOfTopTwoCategories;
  }

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @EqualsAndHashCode
  @ToString
  public static class APlusContent {
    private List<APlusSegment> segments;
  }

  @Getter
  @Setter
  @EqualsAndHashCode
  @ToString
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class APlusSegment {
    private List<HtmlImageElement> images;
  }

  @Getter
  @Setter
  @EqualsAndHashCode
  @ToString
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class HtmlImageElement {
    private String alt;
  }

}
