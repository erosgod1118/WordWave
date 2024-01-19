package co.polarpublishing.common.client;

import co.polarpublishing.common.dto.AudioBookRoyaltyEstimationResponse;
import co.polarpublishing.common.dto.BookSalesForPeriodsResponse;
import co.polarpublishing.common.dto.BookSalesRequest;
import co.polarpublishing.common.dto.KDPSalesRankResponse;
import co.polarpublishing.common.dto.KindleRoyaltyEstimationResponse;
import co.polarpublishing.common.dto.PrintBookRoyaltyEstimationResponse;
import co.polarpublishing.common.dto.PrintBookSalesRangeEstimatesResponse;
import co.polarpublishing.common.dto.SalesEstimate;

import com.keepa.api.backend.structs.AmazonLocale;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * Client providing formula related services.
 *
 * @author FMRGJ
 */
@FeignClient(url = "calculation-engine-service", name = "calculationClient")
@RequestMapping("/api/v1/calculation-engine")
public interface CalculationClient {

  @RequestMapping(value = "/kdp", method = RequestMethod.GET)
  KDPSalesRankResponse getKDPUnitSales(@RequestParam("ranking") int ranking);

  @RequestMapping(value = "/marketplaces/{marketplaceId}/formats/kindle/estimates/sales", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  SalesEstimate getKindleSalesEstimates(@PathVariable("marketplaceId") long marketplaceId,
      @RequestParam(value = "bsr", required = true) int bsr);

  @RequestMapping(value = "/printBookSalesRank/{country}", method = RequestMethod.GET)
  PrintBookSalesRangeEstimatesResponse getPrintBookSalesRank(
      @RequestParam("storeRanking") int storeRanking, @PathVariable("country") String country);

  @GetMapping(value = "/printBookBsr/{country}")
  Integer getPrintBookBsr(@RequestParam("currentSales") int currentSales,
      @PathVariable("country") AmazonLocale country);

  @GetMapping(value = "/currentSales/{country}")
  Integer bsrToSale(@RequestParam("bsr") int bsr,
      @PathVariable("country") String country);

  @PostMapping(value = "/currentSales/{country}")
  List<Integer> bsrsToSales(@RequestBody List<Integer> bsrList, @PathVariable("country") String country);

  @RequestMapping(value = "/royaltyEstimationKindleBook/{country}", method = RequestMethod.GET)
  KindleRoyaltyEstimationResponse getRoyaltyEstimationKindleBook(
      @RequestParam("price") Double price,
      @PathVariable("country") String country);

  @RequestMapping(value = "/royaltyEstimationAudioBook", method = RequestMethod.GET)
  AudioBookRoyaltyEstimationResponse getRoyaltyEstimationAudioBook(
      @RequestParam("price") String price);

  @RequestMapping(value = "/royaltyEstimationPrintBook/{country}", method = RequestMethod.GET)
  PrintBookRoyaltyEstimationResponse getRoyaltyEstimationPrintBook(
      @RequestParam("price") Double price,
      @RequestParam("pageNumber") int pageNumber, @RequestParam("publisher") String publisher,
      @PathVariable("country") String country);

  @PostMapping(value = "/getSalesRank", produces = MediaType.APPLICATION_JSON_VALUE)
  BookSalesForPeriodsResponse getSalesRank(@RequestBody BookSalesRequest bookSalesList);

}
