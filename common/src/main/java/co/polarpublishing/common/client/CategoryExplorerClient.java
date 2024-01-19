package co.polarpublishing.common.client;

import co.polarpublishing.common.dto.BookCategory;
import co.polarpublishing.common.dto.CategoryAnalyticsDataRecord;
import co.polarpublishing.common.dto.ParentCategory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "categoryExplorerClient", url = "category-explorer-service")
@RequestMapping("/api/v1/categoryexplorer")
public interface CategoryExplorerClient {

  @GetMapping(value = "/category", consumes = "application/json", produces = "application/json")
  ResponseEntity<ParentCategory> getParentCategory(@RequestParam("marketplaceId") int marketplaceId,
      @RequestParam("storeId") int storeId,
      @RequestParam("categoryPath") String categoryPath);

  @RequestMapping(value = "/category/analytics", method = RequestMethod.PUT, consumes = "application/json")
  ResponseEntity<String> updateCategoryAnalyticsRecord(
      @RequestBody CategoryAnalyticsDataRecord categoryAnalyticsDataRecord);

  @GetMapping(value = "/subcategories/top")
  long[] getTopSubCategories(@RequestParam long[] categoryIds, @RequestParam Long marketplaceId,
      @RequestParam int limit);

  @GetMapping(value = "/category/known")
  ResponseEntity<List<BookCategory>> getKnownCategories(@RequestParam int marketplaceId, @RequestParam int storeId);
}
