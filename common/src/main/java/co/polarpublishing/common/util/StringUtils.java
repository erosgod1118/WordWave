package co.polarpublishing.common.util;

public class StringUtils {

	public static String cleanForNumber(String numString) {
		if (numString == null) {
			return null;
		}

		return numString.trim().replaceAll("[ ,\\.]", "");
	}

}
