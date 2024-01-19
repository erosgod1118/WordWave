package co.polarpublishing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookReview {

	@EqualsAndHashCode.Include
	private String id;

	private String asin;

	private Long marketplaceId;

	private Integer stars;

	private String title;
	private String text;
	private String date;

}
