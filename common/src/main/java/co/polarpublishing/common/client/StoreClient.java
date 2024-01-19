package co.polarpublishing.common.client;

import co.polarpublishing.common.dto.StoreDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "storeClient", url = "administration-service")
@RequestMapping("/api/v1/marketplaces/{marketplaceId}/stores")
public interface StoreClient {

  @PutMapping(value = "/{storeId}/rootcategory", consumes = "application/json", produces = "application/json")
  void updateStoreWithRootCategoryId(@PathVariable("marketplaceId") int marketplaceId,
      @PathVariable("storeId") int storeId,
      @RequestBody Long rootCategoryId);

  @GetMapping(value = "/{storeId}", consumes = "application/json", produces = "application/json")
  StoreDto getStore(@PathVariable("marketplaceId") Long marketplaceId,
      @PathVariable("storeId") Long storeId);

}
