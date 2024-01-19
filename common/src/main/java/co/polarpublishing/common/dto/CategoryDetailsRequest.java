package co.polarpublishing.common.dto;

import javax.validation.constraints.NotNull;

import co.polarpublishing.common.model.StoreTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryDetailsRequest {

	@NotNull(message = "Please specify a url")
	private String url;

	@NotNull(message = "Please specify a category level (i.e. 1 for L1/Books)")
	private int categoryLevel;

	@NotNull(message = "Please specify a store id (i.e. 1 for Paperback/Hardcover)")
	private int storeId;

	@NotNull(message = "Please specify a marketplace id (i.e. 1 for UK)")
	private int marketplaceId;

	private int depth;

	private StoreTypeName storeTypeName;

}
