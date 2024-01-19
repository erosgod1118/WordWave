package co.polarpublishing.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchResultDto {

	private List<BookDetailsResponse> searchResultBooks;
	private List<BookDetailsResponse> keepaBooks;

	private Integer competitorsQuantity;

}
