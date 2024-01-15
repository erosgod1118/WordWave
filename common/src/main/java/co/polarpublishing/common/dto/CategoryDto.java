package co.polarpublishing.common.dto;

import lombok.Data;

@Data
public class CategoryDto {

	private long id;
	private long categoryId;
	private String name;
	private String path;
	private long marketplaceId;
	private int categoryLevel;
	private long storeId;
	private String url;
	private long parentCategoryId;

}
