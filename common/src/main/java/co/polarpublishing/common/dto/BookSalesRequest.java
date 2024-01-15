package co.polarpublishing.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BookSalesRequest {

	@NotNull
	private String country;

	@JsonProperty("num_of_pages")
	private Integer numOfPages;

	@NotBlank
	@JsonProperty("book_type")
	private String bookType;

	private String author;

	@NotEmpty
	@JsonProperty("book_sales_list")
	private List<BookSales> bookSalesList;

}
