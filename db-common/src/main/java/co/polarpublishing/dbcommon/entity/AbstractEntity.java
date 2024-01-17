package co.polarpublishing.dbcommon.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class AbstractEntity {

	protected Long creationTimestamp;
	protected Long updationTimestamp;

	@PrePersist
	protected void populateCreationTimestamp() {
		this.creationTimestamp = System.currentTimeMillis();
	}

	@PreUpdate
	protected void populateUpdatedTimestamp() {
		this.updationTimestamp = System.currentTimeMillis();
	}

}
