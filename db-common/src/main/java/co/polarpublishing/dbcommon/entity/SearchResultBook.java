package co.polarpublishing.dbcommon.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity representing a particular search result book.
 *
 * @author mani
 */
@Entity
@Table(name = "keywords_search_results_books")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SearchResultBook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;
  @Column(name = "search_page_number")
  private int pageNumber;
  @Column(name = "search_page_item_index")
  private Integer pageItemIndex;
  @ToString.Exclude
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumns( {
      @JoinColumn(name = "asin"),
      @JoinColumn(name = "marketplace_id")
  })
  private BookModel book;
  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "keyword_search_result_id")
  private SearchResult searchResult;
}
