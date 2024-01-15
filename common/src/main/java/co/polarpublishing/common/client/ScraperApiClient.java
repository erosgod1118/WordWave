package co.polarpublishing.common.client;

import co.polarpublishing.common.client.config.ScraperApiClientConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "scraperapi", url = "http://api.scraperapi.com", configuration = ScraperApiClientConfig.class)
public interface ScraperApiClient {

  @RequestMapping(method = RequestMethod.GET)
  String getPage(@RequestParam String url);

}
