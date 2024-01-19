package co.polarpublishing.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "userKeywordSearchResultClient", url = "keyword-research-service")
@RequestMapping("/api/v1/search-result")
public interface UserKeywordSearchResultClient {

	@DeleteMapping(path = "/user-searches")
	void deleteUserSearches();

}
