package co.polarpublishing.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookSalesForPeriodsResponse {

	@JsonProperty("all_daily_sales")
	private List<BookSalesForPeriod> allDailySales;

	@JsonProperty("daily_sales_for_week")
	private List<BookSalesForPeriod> dailySalesForWeek;

	@JsonProperty("weekly_sales_for_two_weeks")
	private List<BookSalesForPeriod> weeklySalesForTwoWeeks;

	@JsonProperty("weekly_sales_for_month")
	private List<BookSalesForPeriod> weeklySalesForMonth;

	public List<BookSalesForPeriod> getAllDailySales() {
		return allDailySales;
	}

	public void setAllDailySales(List<BookSalesForPeriod> allDailySales) {
		this.allDailySales = allDailySales;
	}

	public List<BookSalesForPeriod> getDailySalesForWeek() {
		return dailySalesForWeek;
	}

	public void setDailySalesForWeek(List<BookSalesForPeriod> dailySalesForWeek) {
		this.dailySalesForWeek = dailySalesForWeek;
	}

	public List<BookSalesForPeriod> getWeeklySalesForTwoWeeks() {
		return weeklySalesForTwoWeeks;
	}

	public void setWeeklySalesForTwoWeeks(List<BookSalesForPeriod> weeklySalesForTwoWeeks) {
		this.weeklySalesForTwoWeeks = weeklySalesForTwoWeeks;
	}

	public List<BookSalesForPeriod> getWeeklySalesForMonth() {
		return weeklySalesForMonth;
	}

	public void setWeeklySalesForMonth(List<BookSalesForPeriod> weeklySalesForMonth) {
		this.weeklySalesForMonth = weeklySalesForMonth;
	}

}
