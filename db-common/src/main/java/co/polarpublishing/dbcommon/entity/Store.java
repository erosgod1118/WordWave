package co.polarpublishing.dbcommon.entity;

import co.polarpublishing.common.model.StoreTypeName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing store/department in Amazon.
 *
 * @author mani
 */
@Entity
@Table(name = "stores")
@Setter
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties( {"hibernateLazyInitializer", "handler", "marketplace", "storeType", "searchResults"})
public class Store {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  @Column(name = "amazon_search_alias")
  private String amazonSearchAlias;
  @Column(name = "amazon_id")
  private String amazonId;
  @ManyToOne
  @JoinColumn(name = "marketplace_id")
  private Marketplace marketplace;
  @Transient
  private StoreTypeName type;

  @ManyToOne
  @JoinColumn(name = "type_id")
  private StoreType storeType;
  @Column(name = "root_category_id")
  private Long rootCategoryId;

  @OneToMany
  private List<SearchResult> searchResults;

  @Override
  public String toString() {
    return "Store{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", amazonSearchAlias='" + amazonSearchAlias + '\'' +
        ", amazonId='" + amazonId + '\'' +
        ", storeType=" + storeType +
        ", rootCategoryId=" + rootCategoryId +
        '}';
  }
}
