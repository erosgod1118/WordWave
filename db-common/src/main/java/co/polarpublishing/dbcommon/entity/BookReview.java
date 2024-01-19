package co.polarpublishing.dbcommon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
@Builder
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookReview extends AbstractEntity {

	@Id
	@EqualsAndHashCode.Include
	private String id;

	private String asin;
	private Long marketplaceId;
	private Integer stars;
	private String title;
	private String text;
	private String date;

	public co.polarpublishing.common.dto.BookReview toDto() {
		return co.polarpublishing.common.dto.BookReview.builder()
						.id(id)
						.asin(asin)
						.marketplaceId(marketplaceId)
						.stars(stars)
						.title(title)
						.text(text)
						.date(date)
						.build();
	}

}
