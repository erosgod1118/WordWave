package co.polarpublishing.dbcommon.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class Analytic {

  protected Long creationTimestamp;

  @PrePersist
  protected void populateCreationTimestamp() {
    this.creationTimestamp = System.currentTimeMillis();
  }
  
}
