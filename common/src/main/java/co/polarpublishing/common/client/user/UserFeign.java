package co.polarpublishing.common.client.user;

import co.polarpublishing.common.dto.UserDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "userClient", url = "user-service")
@RequestMapping("/api/v1/users")
public interface UserFeign {

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  ResponseEntity<UserDto> findById(@PathVariable("id") Long id);

  @RequestMapping(value = "/current", method = RequestMethod.GET)
  ResponseEntity<UserDto> findByEmail(@RequestHeader("Authorization") String token);

}
