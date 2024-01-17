package co.polarpublishing.dbcommon.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity representing Amazon's marketplace.
 *
 * @author mani
 */
@Entity
@Table(name = "marketplaces")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"country", "stores", "hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Marketplace {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  private Long id;
  private String name;
  @Column(name = "amazon_id")
  private String amazonId;
  @Column(name = "priority_level")
  private Integer priorityLevel;
  @OneToMany(mappedBy = "marketplace", orphanRemoval = true)
  @ToString.Exclude
  private List<Store> stores;
  @OneToOne
  @JoinColumn(name = "country_id")
  @ToString.Exclude
  private Country country;
  @Column(name = "suggestions_url")
  private String suggestionsUrl;


  @Override
  public String toString() {
    return "Marketplace{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", amazonId='" + amazonId + '\'' +
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
    Marketplace that = (Marketplace) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }
}
