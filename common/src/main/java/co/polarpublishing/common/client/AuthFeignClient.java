package co.polarpublishing.common.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthFeignClient {

  private final AuthFeign authFeign;

  @Autowired
  public AuthFeignClient(AuthFeign authFeign) {
    this.authFeign = authFeign;
  }

  @HystrixCommand(commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "120000"),
  })
  public ResponseEntity<String> tokenStatus(String accessToken) {
    return authFeign.tokenStatus(accessToken);
  }
}
