package co.polarpublishing.dbcommon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "categories")
@ToString(exclude = {"marketplace", "store", "parentCategory"})
@EqualsAndHashCode(exclude = {"marketplace", "store", "parentCategory"})
public class CategoryModel implements Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "CATEGORY_ID")
	private long categoryId;

	@Column(name = "NAME")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MARKETPLACE_ID")
	private Marketplace marketplace;

	@Column(name = "CATEGORY_LEVEL")
	private int categoryLevel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STORE_ID")
	private Store store;

	@Column(name = "URL")
	private String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_CATEGORY_ID")
	private CategoryModel parentCategory;

	@Column(name = "path")
	private String path;

	@Column(name = "products_count")
	private Long productsCount;
	
}
