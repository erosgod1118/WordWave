package co.polarpublishing.common.client.config;

import co.polarpublishing.common.client.interceptor.ScrapingBeeClientInterceptor;
import org.springframework.context.annotation.Bean;

public class ScrapingBeeClientConfig {
  @Bean
  public ScrapingBeeClientInterceptor bscRequestInterceptor() {
    return new ScrapingBeeClientInterceptor();
  }
}
