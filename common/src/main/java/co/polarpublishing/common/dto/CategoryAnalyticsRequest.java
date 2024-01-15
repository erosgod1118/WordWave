package co.polarpublishing.common.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;

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
public class CategoryAnalyticsRequest {

	@NotNull(message = "Please specify a url to the book category")
	private String url;

	@NotNull(message = "Please specify a list with best seller book urls as they appear in Amazon BSR result list")
	private Map<Integer, String> bestSellerBookUrls;

	@NotNull(message = "Please a a category level (i.e. 1 for L1/Books)")
	private int categoryLevel;

	@NotNull(message = "Please specify category name i.e. Arts & Crafts")
	private String categoryName;

	@NotNull(message = "Please specify category path i.e. Books > Arts & Crafts")
	private String categoryPath;

	@NotNull(message = "Please specify category id name i.e. 500")
	private long categoryId;

	@NotNull(message = "Please specify store id name i.e. 1 for Paperback/Hardcover")
	private int storeId;

	@NotNull(message = "Please specify marketplace id name i.e. 1 for US Amazon Marketplace, 2 for UK Amazon Marketplace etc.")
	private int marketplaceId;

	private ParentCategory parentCategory;

}
