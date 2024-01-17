package co.polarpublishing.dbcommon.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing user's book relationship in domain model. Technically
 * it's a join table, but to isolate things between user and book resource
 * domain only the association is mapped in this service.
 *
 * @author mani
 */
@Entity
@Table(name = "user_books")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserBookAssociation extends AbstractEntity {

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
  }
}
