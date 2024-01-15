package co.polarpublishing.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookSales {

	@NotBlank
	@JsonProperty("day_date")
	private String dayDate;

	@JsonProperty("book_rank")
	private Integer bookRank;

	@NotNull
	@JsonProperty("book_price")
	private Double bookPrice;

	public Double getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(Double bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getDayDate() {
		return dayDate;
	}

	public void setDayDate(String dayDate) {
		this.dayDate = dayDate;
	}

	public Integer getBookRank() {
		return bookRank;
	}

	public void setBookRank(Integer bookRank) {
		this.bookRank = bookRank;
	}

}
