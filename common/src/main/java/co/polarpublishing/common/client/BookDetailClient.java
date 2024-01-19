package co.polarpublishing.common.client;

import co.polarpublishing.common.dto.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(url = "web-crawler-service", name = "bookDetailClient")
@RequestMapping("/api/v1/webcrawler")
public interface BookDetailClient {

  @PostMapping(value = "/book", produces = "application/json")
  BookDetailsResponse getKindleBookDetail(@RequestBody BookUrlRequest dto);

  @PostMapping(value = "/keepaapi/book", consumes = "application/json", produces = "application/json")
  BookDetailsResponse getKeepaAPIBookDetails(@RequestBody BookUrlRequest bookUrlRequest);

  @PostMapping(value = "/competitors", consumes = "application/json", produces = "application/json")
  SearchResultDto getKeywordCompetitors(@RequestParam String keywords, @RequestParam String marketplaceName,
      @RequestBody List<BookDetailsResponse> searchResultBooks);

  @PostMapping(value = "/keepaapi/niche-finder", consumes = "application/json", produces = "application/json")
  ProductFinderDto getKeepaAPINicheBooks(@RequestBody NicheFinderRequest nicheFinderRequest);

  @PostMapping(value = "/book-reviews", produces = "application/json")
  BookReviewsResponse getBookReviews(@RequestBody BookUrlRequest dto);

}
