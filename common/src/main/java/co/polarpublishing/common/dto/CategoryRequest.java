package co.polarpublishing.common.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryRequest {

	@NotNull(message = "You should specify a marketplace Id")
	private int marketplaceId;

	@NotNull(message = "You should specify a store Id")
	private int storeId;

	@NotNull(message = "You should specify a category path")
	private String categoryPath;

}
