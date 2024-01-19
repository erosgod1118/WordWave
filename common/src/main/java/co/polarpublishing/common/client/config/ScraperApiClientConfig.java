package co.polarpublishing.common.client.config;

import co.polarpublishing.common.client.interceptor.ScraperApiClientInterceptor;
import co.polarpublishing.common.client.decoder.ScraperApiErrorDecoder;
import feign.Request;
import org.springframework.context.annotation.Bean;

public class ScraperApiClientConfig {

  private static int THREE_MINUTES = 180_000;

  @Bean
  public ScraperApiClientInterceptor scraperApiClientInterceptor() {
    return new ScraperApiClientInterceptor();
  }

  @Bean
  public ScraperApiErrorDecoder scraperApiErrorDecoder() {
    return new ScraperApiErrorDecoder();
  }

  @Bean
  public Request.Options options() {
    return new Request.Options(THREE_MINUTES, THREE_MINUTES);
  }

}
