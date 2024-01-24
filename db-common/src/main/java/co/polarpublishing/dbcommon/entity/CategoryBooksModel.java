package co.polarpublishing.dbcommon.entity;

import co.polarpublishing.common.constant.BookType;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "category_books")
@Builder
// @Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryBooksModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "category_id")
	private Long categoryId;

	private Long aod;
	private Integer position;
	private String asin;

	@Column(name = "marketplace_id")
	private Long marketplaceId;

	@Column(name = "store_id")
	private Long storeId;

	private String title;
	private String author;
	private String thumbnail;
	private Long bsr;
	private Double stars;
	private Long ratings;

	@Column(name = "book_type")
	private BookType type;

	private Double price;

	@Column(name = "created_at")
	@Builder.Default
	private Long createdAt = System.currentTimeMillis();

	@Column(name = "updated_at")
	protected Long updatedAt;

	@PreUpdate
	protected void populateUpdatedAt() {
		this.updatedAt = System.currentTimeMillis();
	}

}