package co.polarpublishing.common.client;

import co.polarpublishing.common.dto.CategoryAnalyticsDataRecord;
import co.polarpublishing.common.dto.CategoryAnalyticsRequest;
import co.polarpublishing.common.dto.CategoryDetailsRequest;
import co.polarpublishing.common.dto.CategorySummary;
import co.polarpublishing.common.dto.TrackingKeywordScrapeDto;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "webCrawlerClient", url = "web-crawler-service")
@RequestMapping("/api/v1/webcrawler")
public interface WebCrawlerClient {

  @PostMapping(value = "/category", consumes = "application/json", produces = "application/json")
  ResponseEntity<CategorySummary> getRootBookCategoryDetails(
      @RequestBody CategoryDetailsRequest bookCategoryUrlDtoRequest);

  @PostMapping(value = "/category/analytics", consumes = "application/json", produces = "application/json")
  ResponseEntity<CategoryAnalyticsDataRecord> getRootBookCategoryAnalytics(
      @RequestBody CategoryAnalyticsRequest categoryAnalyticsDtoRequest);

  @PostMapping(value = "/keyword/analytics", consumes = "application/json", produces = "application/json")
  List<TrackingKeywordScrapeDto> getRootBookKeywordAnalytics(
      @RequestBody List<TrackingKeywordScrapeDto> userBookKeywordsList);
}
