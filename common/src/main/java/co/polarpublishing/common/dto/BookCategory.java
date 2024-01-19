package co.polarpublishing.common.dto;

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
public class BookCategory {

	private String url;
	private String categoryName;

	private Long categoryId;

	private int categoryLevel;
	private int storeId;
	private int marketplaceId;

	private String path;

	private ParentCategory parentCategory;

}
