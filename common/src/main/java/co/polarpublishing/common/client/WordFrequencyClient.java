package co.polarpublishing.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "wordFrequencyClient", url = "administration-service")
@RequestMapping("/api/v1/word-frequencies")
public interface WordFrequencyClient {

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  Map<String, Long> getWordFrequencies(@RequestBody List<String> texts);

}
