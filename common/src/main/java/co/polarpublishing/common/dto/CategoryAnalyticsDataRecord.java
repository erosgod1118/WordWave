package co.polarpublishing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryAnalyticsDataRecord {

	private String url;

	private long categoryId;

	private String name;
	private String path;

	private int storeId;

	private Object topRank;
	private Object topSales;

	private long avgBestSellerRank;

	private Object avgPrice;

	private double avgStarRating;

	private long avgReviews;

	private int categoryLevel;

	private ParentCategory parentCategory;

	private Object miscellaneous;

	private List<CategoryBook> categoryBooks;

}
