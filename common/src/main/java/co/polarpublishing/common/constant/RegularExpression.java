package co.polarpublishing.common.constant;

import java.util.regex.Pattern;

/**
 * Collection of common regular expressions.
 *
 * @author FMRGJ
 */
public class RegularExpression {

	public static final String SORTING_DIRECTIONS = "asc|desc";
	public static final String NUMBER_RANGE = "^\\-?(\\d+\\.?\\d*|\\d*\\.?\\d+)-\\-?(\\d+\\" +
			".?\\d*|\\d*\\.?\\d+)$";
	public static final String NUMBER_RANGE_WITH_PLUS_AT_END = "^\\-?(\\d+\\.?\\d*|\\d*\\.?\\d+)" +
			"-\\-?(\\d+\\.?\\d*|\\d*\\.?\\d+)\\+$";
	public static final String APPROXIMATED_NUMBER = "^~\\-?(\\d+\\.?\\d*|\\d*\\.?\\d+)$";
	public static final String LESS_NUMBER = "^<\\-?(\\d+\\.?\\d*|\\d*\\.?\\d+)$";
	public static final String NUMBER = "^\\-?(\\d+\\.?\\d*|\\d*\\.?\\d+)$";
	public static final String NUMBER_WITH_PLUS_AT_END = "^\\-?(\\d+\\.?\\d*|\\d*\\.?\\d+)\\+$";
	public static final String URL = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\" +
			".[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
	public static final String DOMAIN_NAME = "^(?:http:\\/\\/|https:\\/\\/|www.|)(?:www.)?(?:.+?)" +
			"(\\..*?)(?:$|\\/|\\?)";
	public static final String MARKETPLACE_NAME_PATTERN = "(amazon|audible)\\.(com|co.uk|ca|com.mx|de|it|fr|es|com.au|in|nl|se|pr|tr)";
	public static final Pattern SINGLE_VALUED_ESTIMATION_PATTERN = Pattern.compile("^([<~]{1})?" +
			"(-?\\d+\\.\\d+|-?\\d+)(\\+{1})?$");
	public static final Pattern RANGE_ESTIMATION_PATTERN = Pattern.compile("^([<~]{1})?(-?\\d+\\" +
			".\\d+|-?\\d+)(?:\\s*)?-(?:\\s*)(-?\\d+\\.\\d+|-?\\d+)?(\\+{1})?$");
	public static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

}
