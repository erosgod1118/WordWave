package co.polarpublishing.common.client.user;

import co.polarpublishing.common.dto.UserDto;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClient {

  private final UserFeign userFeign;

  @Autowired
  public UserFeignClient(UserFeign userFeign) {
    this.userFeign = userFeign;
  }

  @HystrixCommand(commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "120000"),
  })
  public ResponseEntity<UserDto> findById(Long id) {
    return userFeign.findById(id);
  }

  @HystrixCommand(commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "120000"),
  })
  public ResponseEntity<UserDto> current(String token) {
    return userFeign.findByEmail(token);
  }

}
