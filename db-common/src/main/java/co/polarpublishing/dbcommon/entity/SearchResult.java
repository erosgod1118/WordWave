package co.polarpublishing.dbcommon.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
 * Entity representing keyword search result in Amazon.
 *
 * @author mani
 */
@Entity
@Table(name = "keyword_search_results")
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchResult {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;
  private long keywordId;
  private long parentKeywordId;

  @ManyToOne
  @JoinColumn(name = "marketplace_id")
  private Marketplace marketplace;

  @ManyToOne
  @JoinColumn(name = "store_id")
  private Store store;

  private long timestamp;
  private Long booksQuantity;
  @OneToMany(mappedBy = "searchResult", orphanRemoval = true,
      cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<SearchResultBook> searchResultBooks;
  private long userId;
  @OneToOne(mappedBy = "searchResult", orphanRemoval = false)
  @ToString.Exclude
  private KeywordAnalytic keywordAnalytic;
  private Long keywordResearchSessionId;

  @PrePersist
  public void setTimestamp() {
    this.timestamp = System.currentTimeMillis();
  }
}
