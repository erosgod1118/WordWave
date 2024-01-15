package co.polarpublishing.common.util;

// import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
// @Slf4j
public class AmazonUtil {

	public String buildAmazonUrl(String marketplaceName, String asin) {
		StringBuilder urlBuilder = new StringBuilder("https://www.amazon").append(marketplaceName)
				.append(".de".equals(marketplaceName) ? "/-/en" : "") // en translation
				.append("/dp/")
				.append(asin);

		return urlBuilder.toString();
	}

}
