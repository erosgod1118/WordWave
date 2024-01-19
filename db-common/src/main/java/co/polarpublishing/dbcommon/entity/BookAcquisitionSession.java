package co.polarpublishing.dbcommon.entity;

import co.polarpublishing.common.constant.BookType;
import co.polarpublishing.common.constant.TaskStatus;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books_acquisition_sessions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookAcquisitionSession extends AbstractEntity {

  @EmbeddedId
  private Id id;

  @Enumerated(value = EnumType.STRING)
  private TaskStatus status;

  @Enumerated(value = EnumType.STRING)
  private BookType type;

  @Embeddable
  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Id implements Serializable {

    private String asin;
    private Long marketplaceId;
    
  }

}
