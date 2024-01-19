package co.polarpublishing.common.util;

import java.math.BigDecimal;

/**
 * Utility class for doing basic maths.
 *
 * @author FMRGJ
 */
public class MathUtil {

	public static double round(double value, int scale) {
		return round(value, scale, BigDecimal.ROUND_HALF_UP);
	}

	public static double round(double value, int scale, int roundingMethod) {
		try {
			return (new BigDecimal(Double.toString(value))
					.setScale(scale, roundingMethod))
					.doubleValue();
		} catch (NumberFormatException ex) {
			if (Double.isInfinite(value)) {
				return value;
			} else {
				return Double.NaN;
			}
		}
	}

	public static double mean(double[] values) {
		double total = 0;
		for (Double value : values) {
			total += value;
		}
		return round(total / values.length, 2);
	}

	public static long mean(long[] values) {
		double total = 0;
		for (long value : values) {
			total += value;
		}
		return new Double(total / values.length).longValue();
	}

	public static long total(long[] values) {
		long total = 0;
		for (Long value : values) {
			total += value;
		}
		return total;
	}

	public static String multiplyRangeByNumber(String range, double multiplier, boolean includeDecimalPlaces) {
		String[] rangeNumbers = range.split("-");

		if (includeDecimalPlaces) {
			return round(
					(multiplier * Double.parseDouble(rangeNumbers[0].trim())), 2) + "-"
					+ round((multiplier * Double.parseDouble(rangeNumbers[1].trim())), 2);
		}

		return Math.floor(
				multiplier * Double.parseDouble(rangeNumbers[0].trim())) + "-"
				+ Math.floor(
						multiplier * Double.parseDouble(rangeNumbers[1].trim()));
	}

	public static String multiplyRangeByRange(String multiplicand, String multiplier, boolean includeDecimalPlaces) {
		String[] multiplicands = multiplicand.split("-");
		String[] multipliers = multiplier.split("-");

		double minimum = round(
				Double.parseDouble(multiplicands[0]) * Double.parseDouble(
						multipliers[0]),
				2);
		double maximum = round(
				Double.parseDouble(multiplicands[1]) * Double.parseDouble(
						multipliers[1]),
				2);

		if (includeDecimalPlaces) {
			return minimum + "-" + maximum;
		}

		return Math.floor(minimum) + "-" + Math.floor(maximum);
	}

	public static double mean(int[] values) {
		double total = 0;

		for (int value : values) {
			total += value;
		}

		return round(total / values.length, 2);
	}

}
