package co.polarpublishing.common.client.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
// import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

// @Slf4j
public class ScrapingBeeClientInterceptor implements RequestInterceptor {

  @Value("${keyword-generator.scraperapi.key}")
  private String apiKey;

  @Value("${keyword-generator.scraperapi.auth.param}")
  private String authHeader;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate.query(authHeader, apiKey);
  }

}
