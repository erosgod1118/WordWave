package co.polarpublishing.dbcommon.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a user book keyword.
 *
 * @author FMRGJ
 */
@Entity
@Table(name = "user_book_keywords")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserBookKeywordAssociation {

  @EmbeddedId
  private Id id;

  @Embeddable
  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  public static class Id implements Serializable {

    private Long userId;
    private String asin;
    private Long marketplaceId;

    @ManyToOne
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;
    
    private Long storeId;

  }

}
