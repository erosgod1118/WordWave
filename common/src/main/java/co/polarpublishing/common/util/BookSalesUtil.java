package co.polarpublishing.common.util;

import co.polarpublishing.common.dto.BookSalesForPeriod;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Function;

@Slf4j
public class BookSalesUtil {

	public static int calcSum(List<BookSalesForPeriod> dailySales, Function<BookSalesForPeriod, String> mapper) {
		if (dailySales == null) {
			return 0;
		}

		try {
			return dailySales.stream()
					.map(mapper)
					.filter(StringUtils::isNotEmpty)
					.mapToInt(val -> Double.valueOf(val).intValue())
					.sum();
		} catch (NumberFormatException e) {
			log.error("No sales to calculate sum");
			return 0;
		}
	}

}
