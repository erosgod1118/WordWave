package co.polarpublishing.common.constant;

public enum KeywordAnalyticMiscellaneous {

	COMPETITION_SCORE("competition_score"),
	MARKET_SIZE_SCORE("market_size_score"),
	OPPORTUNITY_SCORE("opportunity_score"),
	AVERAGE_MAX_MONTHLY_SALES("average_max_monthly_sales"),
	AVERAGE_MIN_MONTHLY_SALES("average_min_monthly_sales"),
	AVERAGE_MAX_MONTHLY_EARNING("average_max_monthly_earnings"),
	AVERAGE_MIN_MONTHLY_EARNING("average_min_monthly_earnings"),
	SEARCH_RESULT_BOOKS_QUANTITY("search_result_books_quantity"),
	FIRST_PAGE_AVERAGE_BESTSELLER_RANK("first_page_average_best_seller_rank");

	private String name;

	KeywordAnalyticMiscellaneous(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
