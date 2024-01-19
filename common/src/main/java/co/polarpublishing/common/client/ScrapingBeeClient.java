package co.polarpublishing.common.client;

import co.polarpublishing.common.client.config.ScrapingBeeClientConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "scrapingbee", url = "https://app.scrapingbee.com/api/v1/", configuration = ScrapingBeeClientConfig.class)
public interface ScrapingBeeClient {

  @RequestMapping(method = RequestMethod.GET)
  String getPage(@RequestParam String url);

}
