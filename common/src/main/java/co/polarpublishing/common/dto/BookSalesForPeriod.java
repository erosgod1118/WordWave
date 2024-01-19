package co.polarpublishing.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookSalesForPeriod {

	private String time;
	private String sales;

	@JsonProperty("sales_average")
	private String salesAverage;

	private String royalties;

	public String getRoyalties() {
		return royalties;
	}

	public void setRoyalties(String royalties) {
		this.royalties = royalties;
	}

	public String getSalesAverage() {
		return salesAverage;
	}

	public void setSalesAverage(String salesAverage) {
		this.salesAverage = salesAverage;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

}
