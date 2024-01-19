package co.polarpublishing.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "authClient", url = "user-service")
@RequestMapping("/api/v1/auth")
public interface AuthFeign {

  @RequestMapping(value = "/token-status", method = RequestMethod.GET)
  ResponseEntity<String> tokenStatus(@RequestHeader("Authorization") String accessToken);

}
