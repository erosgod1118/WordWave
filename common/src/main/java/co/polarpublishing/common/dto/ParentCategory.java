package co.polarpublishing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ParentCategory {

	private long id;

	private String name;
	private String url;

	private int categoryLevel;
	private int storeId;
	private int marketplaceId;

	private String path;

	private long parentCategoryId;

}
