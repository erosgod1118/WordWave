package co.polarpublishing.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class CategorySummary {

	private List<BookCategory> bookCategoryNextLevelDetails;
	private List<CategoryBook> categoryBooks;
	private List<String> bestSellerBookUrls;

	private int categoryLevel;

	private String categoryName;
	private String categoryPath;

	private long categoryId;

	private int storeId;
	private int marketplaceId;

	private String url;

}
