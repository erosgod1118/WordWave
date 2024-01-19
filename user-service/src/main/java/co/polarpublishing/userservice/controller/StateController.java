package co.polarpublishing.userservice.controller;

import co.polarpublishing.common.util.JwtUtil;
import co.polarpublishing.userservice.dto.UserState;
import co.polarpublishing.userservice.exception.UserNotFoundException;
import co.polarpublishing.userservice.service.StateManagementService;
import co.polarpublishing.userservice.service.dto.ChromeExtensionStateDto;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/states")
@RequiredArgsConstructor
public class StateController {

  private final StateManagementService stateManagementService;
  private final JwtUtil jwtUtil;

  @GetMapping("/chrome-extension")
  public ResponseEntity getChromeExtensionState(HttpServletRequest request) {
    String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    Long userId = null;
    if (accessToken != null && !accessToken.trim().isEmpty()) {
      userId = this.jwtUtil.getUser(accessToken).getUserId();
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    try {
      ChromeExtensionStateDto stateDto =
          this.stateManagementService.getChromeExtensionState(userId);
      return ResponseEntity.ok(stateDto);
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @GetMapping
  public UserState getState(HttpServletRequest request) throws UserNotFoundException {

    String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    Long userId = this.jwtUtil.getUser(accessToken).getUserId();

    UserState foundUserState = this.stateManagementService.getUserState(userId);

    return foundUserState;
  }
}
