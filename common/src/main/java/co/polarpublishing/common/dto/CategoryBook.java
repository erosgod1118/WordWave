package co.polarpublishing.common.dto;

import co.polarpublishing.common.constant.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBook {

	private String asin;
	private Long marketplaceId;
	private Long storeId;
	private String title;
	private String author;
	private String thumbnail;
	private Long bsr;
	private Double stars;
	private Long ratings;
	private BookType type;
	private Double price;

}
