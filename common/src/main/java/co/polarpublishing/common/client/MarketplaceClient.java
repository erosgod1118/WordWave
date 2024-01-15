package co.polarpublishing.common.client;

import co.polarpublishing.common.dto.MarketplaceDto;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "marketplaceClient", url = "administration-service")
@RequestMapping(path = "/api/v1/marketplaces")
public interface MarketplaceClient {

  @RequestMapping(method = RequestMethod.GET, params = { "name", "sort", "dir", "page", "size" })
  List<MarketplaceDto> getMarketplaces(
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "sort", defaultValue = "name", required = false) String sortingProperty,
      @RequestParam(name = "dir", defaultValue = "ASC", required = false) String sortingDirection,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "size", defaultValue = "50", required = false) int pageSize);

  @GetMapping(path = "/{marketplaceId}")
  MarketplaceDto getMarketplace(@PathVariable("marketplaceId") Long marketplaceId);

  @RequestMapping(method = RequestMethod.GET)
  List<MarketplaceDto> getMarketplaces();

  @GetMapping(path = "/country")
  String getCountryCode(@RequestParam Long marketplaceId);

  @GetMapping(path = "/{marketplaceName}")
  MarketplaceDto getMarketplaceByName(@PathVariable("marketplaceName") String name);

}
