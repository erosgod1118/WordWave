package co.polarpublishing.dbcommon.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a country.
 *
 * @author mani
 */
@Entity
@Table(name = "countries")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  // ISO Alpha 2 code is standardized in app.
  private String code;
  // https://developers.google.com/adwords/api/docs/appendix/codes-formats
  @Column(name = "google_location_id")
  private Long googleLocationId;
  // https://developers.google.com/google-ads/api/reference/data/geotargets
  @Column(name = "google_language_id")
  private Long googleLanguageId;

  @Override
  public String toString() {
    return "Country{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", code='" + code + '\'' +
        '}';
  }
}
