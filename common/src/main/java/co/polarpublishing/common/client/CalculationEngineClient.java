package co.polarpublishing.common.client;

import co.polarpublishing.common.dto.CompetitionScoreResponse;
import co.polarpublishing.common.dto.MarketSizeScoreResponse;
import co.polarpublishing.common.dto.OpportunityScoreRespone;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "calculationEngineClient", url = "calculation-engine-service")
@RequestMapping("/api/v1/krt-analytis-engine")
public interface CalculationEngineClient {

  @RequestMapping(value = "/marketsizescore", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  MarketSizeScoreResponse getMarketSizeScore(@RequestParam("averageranking") int averageranking);

  @RequestMapping(value = "/competitorscore", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  CompetitionScoreResponse getCompetitionScore(@RequestParam("competitors") int competitors,
      @RequestParam("reviews") int reviews);

  @RequestMapping(value = "/opportunityscore", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  OpportunityScoreRespone getOpportunityScore(@RequestParam("competitors") int competitors,
      @RequestParam("reviews") int reviews, @RequestParam("salesRank") int salesRank);

}
