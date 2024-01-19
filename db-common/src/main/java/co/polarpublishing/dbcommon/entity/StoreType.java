package co.polarpublishing.dbcommon.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import co.polarpublishing.common.model.StoreTypeName;

import lombok.Data;

@Entity
@Table(name = "store_types")
@Data
public class StoreType implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name")
	@Enumerated(EnumType.STRING)
	private StoreTypeName storeTypeName;
	
}
