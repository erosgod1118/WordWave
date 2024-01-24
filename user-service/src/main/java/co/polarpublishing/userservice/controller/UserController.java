package co.polarpublishing.userservice.controller;

import co.polarpublishing.common.constant.RoleName;
import co.polarpublishing.common.dto.ErrorResponse;
import co.polarpublishing.common.dto.UserDto;
import co.polarpublishing.common.exception.NoAccessRightsException;
import co.polarpublishing.common.util.DateAndTimeUtil;
import co.polarpublishing.userservice.dto.*;
import co.polarpublishing.userservice.entity.UserLimit;
import co.polarpublishing.userservice.exception.UserNotFoundException;
import co.polarpublishing.userservice.service.JwtService;
import co.polarpublishing.userservice.service.UserLimitService;
import co.polarpublishing.userservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
// import org.hibernate.mapping.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RestController
@EnableScheduling
@RequestMapping("/api/v1/users")
@Api(tags = "user-api", description = "User Service REST APIs")
@RequiredArgsConstructor
public class UserController {

  private final UserLimitService userLimitService;
  private final UserService userService;
  private final JwtService jwtService;

  @Value("${upload.file.path}")
  private String USER_UPLOAD_AVATAR_PATH;

  @Value("${users.unsubscribe-reasons.jobs.mail}")
  private String cancellationMail;

  @ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success. Returns user by id."),
    @ApiResponse(code = 404, message = "Failure. Indicates the user does not exist with such id.")
  })
  @RequestMapping(value = "/current", method = RequestMethod.GET)
  public ResponseEntity findByEmail(HttpServletRequest request) {
    try {
      TokenInfo tokenInfo = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
      UserDto userDto = userService.findByEmail(tokenInfo.getEmail());
      userDto.setLimits(userLimitService.getAllLimits(tokenInfo.getUserId()));

      return ResponseEntity.ok(userDto);

    } catch (UserNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    }
  }

  @ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success. Returns user by id."),
    @ApiResponse(code = 404, message = "Failure. Indicates the user does not exist with such id.")
  })
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity findById(@PathVariable Long id) {
    try {
      UserDto userDto = userService.findById(id);

      return ResponseEntity.ok(userDto);
    } catch (UserNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    }
  }

  @ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
  @ApiResponses(value = {
    @ApiResponse(code = 204, message = "Success. Password was changed successfully."),
    @ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
    @ApiResponse(code = 404, message = "Failure. Indicates the user does not exist with such id.")
  })
  @RequestMapping(value = "/current/change-password", method = RequestMethod.PUT)
  public ResponseEntity changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto, HttpServletRequest request) {
    try {
      TokenInfo tokenInfo = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
      UserDto userDto = userService.findByEmail(tokenInfo.getEmail());

      userService.changePassword(userDto.getUserId(), changePasswordDto);

      return ResponseEntity.noContent().build();
    } catch (UserNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    }
  }

  @ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success. Returns updated user object."),
    @ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
    @ApiResponse(code = 404, message = "Failure. Indicates the user does not exist")
  })
  @RequestMapping(value = "/current", method = RequestMethod.PUT)
  public ResponseEntity update(@Valid @RequestBody UpdateUserDto updateUserDto, HttpServletRequest request) {
    try {
      TokenInfo tokenInfo = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
      UserDto userDto = userService.findByEmail(tokenInfo.getEmail());

      userDto.setFirstName(updateUserDto.getFirstName());
      userDto.setLastName(updateUserDto.getLastName());

      UserDto updatedUser = userService.update(userDto.getUserId(), userDto);
      return ResponseEntity.ok(updatedUser);
    } catch (UserNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    }
  }

  @ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success. Promotes user."),
    @ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
    @ApiResponse(code = 404, message = "Failure. Indicates the user does not exist")
  })
  @RequestMapping(value = "/promote-to-pro", method = RequestMethod.PUT)
  public ResponseEntity promoteToPro(long userId, HttpServletRequest request) {
    try {
      TokenInfo tokenInfo = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
      UserDto userDto = userService.findByEmail(tokenInfo.getEmail());

      if (userDto.getRoles().stream().noneMatch(r -> r.getRole().equals(RoleName.ROLE_ADMIN))) {
        throw new NoAccessRightsException();
      }

      return ResponseEntity.noContent().build();
    } catch (NoAccessRightsException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(e.getCode())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    } catch (UserNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    }
  }

  @ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success. Demotes user."),
    @ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
    @ApiResponse(code = 404, message = "Failure. Indicates the user does not exist")
  })
  @RequestMapping(value = "/demote", method = RequestMethod.PUT)
  public ResponseEntity demote(long userId, HttpServletRequest request) {
    try {
      TokenInfo tokenInfo = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
      UserDto userDto = userService.findByEmail(tokenInfo.getEmail());

      if (userDto.getRoles().stream().noneMatch(r -> r.getRole().equals(RoleName.ROLE_ADMIN))) {
        throw new NoAccessRightsException();
      }

      return ResponseEntity.noContent().build();
    } catch (NoAccessRightsException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(e.getCode())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    } catch (UserNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    }
  }

  @ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success. Bans user."),
    @ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
    @ApiResponse(code = 404, message = "Failure. Indicates the user does not exist")
  })
  @RequestMapping(value = "/ban", method = RequestMethod.PUT)
  public ResponseEntity ban(long userId, HttpServletRequest request) {
    try {
      TokenInfo tokenInfo = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
      UserDto userDto = userService.findByEmail(tokenInfo.getEmail());

      if (userDto.getRoles().stream().noneMatch(r -> r.getRole().equals(RoleName.ROLE_ADMIN))) {
        throw new NoAccessRightsException();
      }

      return ResponseEntity.noContent().build();
    } catch (NoAccessRightsException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(e.getCode())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    } catch (UserNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    }
  }

  @ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success. Unbans user."),
    @ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
    @ApiResponse(code = 404, message = "Failure. Indicates the user does not exist")
  })
  @RequestMapping(value = "/unban", method = RequestMethod.PUT)
  public ResponseEntity unban(long userId, HttpServletRequest request) {
    try {
      TokenInfo tokenInfo = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
      UserDto userDto = userService.findByEmail(tokenInfo.getEmail());

      if (userDto.getRoles().stream().noneMatch(r -> r.getRole().equals(RoleName.ROLE_ADMIN))) {
        throw new NoAccessRightsException();
      }

      return ResponseEntity.noContent().build();
    } catch (NoAccessRightsException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(e.getCode())
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    } catch (UserNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    }
  }

  @ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success. Returns link to avatar in the response body."),
    @ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
    @ApiResponse(code = 404, message = "Failure. Indicates the user does not exist")
  })
  @RequestMapping(value = "/current/upload-avatar", method = RequestMethod.POST)
  public ResponseEntity uploadAvatar(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
    try {
      TokenInfo tokenInfo = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
      UserDto userDto = userService.findByEmail(tokenInfo.getEmail());

      if (file.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(
                ErrorResponse
                  .builder()
                  .code(2006)
                  .message("Please select a photo to upload.")
                  .status(HttpStatus.BAD_REQUEST.value())
                  .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                  .build()
              );
      }

      String filename = String.format("%s.%s", userDto.getUserId(), FilenameUtils.getExtension(file.getOriginalFilename()));
      String filepath = Paths.get(USER_UPLOAD_AVATAR_PATH, filename).toString();
      String avatarLink = String.format("/api/v1/users/avatar/%s", filename);

      // save user's avatar to the specified directory
      Files.write(Paths.get(filepath), file.getBytes());

      // save avatar link
      userDto.setAvatarLink(avatarLink);
      userService.update(userDto.getUserId(), userDto);

      return ResponseEntity.ok(UploadPhotoResponseDto.builder().location(avatarLink).build());
    } catch (UserNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
              ErrorResponse
                .builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(DateAndTimeUtil.getCurrentEpochTime())
                .build()
            );
    }
  }

  @ApiResponses(value = {@ApiResponse(code = 200, message = "Success. Returns user's avatar.")})
  @RequestMapping(value = "/avatar/{fileName:..+}", method = RequestMethod.GET)
  public byte[] getAvatar(@PathVariable String fileName) throws IOException {
    File file = new File(USER_UPLOAD_AVATAR_PATH.concat(fileName));
    return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
  }

  @RequestMapping(value = "/get-wp-data", method = RequestMethod.GET)
  public ResponseEntity<UserWpDataDto> getDataFromWp(@RequestParam(required = false) String userEmail, HttpServletRequest request) throws UserNotFoundException {
    String email = "";
    
    if (userEmail != null && !userEmail.equals("")) {
      email = userEmail;
    } else {
      email = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION)).getEmail();
    }

    if (email == null) {
      throw new UserNotFoundException("User with email: " + userEmail + " not found");
    }

    UserWpDataDto response = userService.getUserWpData(email);
    return ResponseEntity.ok(response);
  }

  @RequestMapping(value = "/get-user-billing-history", method = RequestMethod.GET)
  public ResponseEntity<List<UserBillingHistoryDto>> getUserBillingHistory(@RequestParam(required = false) String userEmail, HttpServletRequest request) throws UserNotFoundException {
    String email = "";
    if (userEmail != null && !userEmail.equals("")) {
      email = userEmail;
    } else {
      email = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION)).getEmail();
    }

    if (email == null) {
      throw new UserNotFoundException("User with email: " + userEmail + " not found");
    }

    List<UserBillingHistoryDto> response = userService.getUserBillingHistory(email);
    return ResponseEntity.ok(response);
  }

  @PutMapping(path = "/update-wp-data", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public ResponseEntity updateWpData(@RequestParam(required = false) String userEmail, HttpServletRequest request) throws UserNotFoundException {
    String email = "";

    if (userEmail != null && !userEmail.equals("")) {
      email = userEmail;
    } else {
      email = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION)).getEmail();
    }

    if (email == null) {
      throw new UserNotFoundException("User with email: " + userEmail + " not found");
    }

    userService.updateUserWpDataForRequest(email);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(value = "/save-new-payment", method = RequestMethod.POST)
  public ResponseEntity saveNewPayment(@RequestBody UserBillingHistoryDto userBillingHistoryDto) throws UserNotFoundException {
    userService.saveBillingHistory(userBillingHistoryDto);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(value = "/update-billing-history", method = RequestMethod.POST)
  public ResponseEntity updateBillingHistory() {
    userService.updateAllBillingHistory();
    return ResponseEntity.ok().build();
  }

  @PutMapping(path = "/change-user-plan", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public ResponseEntity<String> changeUserPlan(@RequestParam(required = false) String userEmail, @RequestParam String planType, HttpServletRequest request) throws UserNotFoundException {
    String email = "";

    if (userEmail != null && !userEmail.equals("")) {
      email = userEmail;
    } else {
      email = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION)).getEmail();
    }

    if (email == null) {
      throw new UserNotFoundException("User with email: " + userEmail + " not found");
    }

    log.info("Changing user plan {} to: {}", email, planType);

    String newPlanType = userService.changeUserSubscription(email, planType);
    return ResponseEntity.ok(newPlanType);
  }

  @PostMapping(path = "/create-customer-portal-session", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public ResponseEntity<StripePortalDto> createCustomerPortal(@RequestParam(required = false) String userEmail, @RequestParam String returnUrl,  HttpServletRequest request) throws UserNotFoundException {
    String email = "";

    if (userEmail != null && !userEmail.equals("")) {
      email = userEmail;
    } else {
      email = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION)).getEmail();
    }

    if (email == null) {
      throw new UserNotFoundException("User with email: " + userEmail + " not found");
    }

    String responseUrl = userService.crateCustomerPortal(email, returnUrl);
    StripePortalDto response = new StripePortalDto();

    response.setUrl(responseUrl);
    return ResponseEntity.ok(response);
  }

  @PostMapping(path = "/unsubscribe-user", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public ResponseEntity<UnsubscribeDto> unsubscribeUser(@RequestParam(required = false) String userEmail, @RequestParam(required = false) String reason, HttpServletRequest request) throws UserNotFoundException {
    String email = "";

    if (userEmail != null && !userEmail.equals("")) {
      email = userEmail;
    } else {
      email = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION)).getEmail();
    }

    if (email == null) {
      throw new UserNotFoundException("User with email: " + userEmail + " not found");
    }

    UnsubscribeDto unsubscribeDto = userService.unsubscribeUser(email, reason);
    return ResponseEntity.ok(unsubscribeDto);
  }

  @PostMapping(path = "/trial-questionnaire")
  public ResponseEntity<TrialQuestionnaireDto> trialQuestionnaire(@RequestBody TrialQuestionnaireDto questionnaire, HttpServletRequest request) throws UserNotFoundException {
    String email = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION)).getEmail();
    TrialQuestionnaireDto res = userService.answerTrialQuestionnaire(email, questionnaire);

    return ResponseEntity.ok(res);
  }

  @GetMapping(path = "/trial-questionnaire")
  public ResponseEntity<TrialQuestionnaireDto> checkQuestionnaire(HttpServletRequest request) throws UserNotFoundException {
    String email = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION)).getEmail();
    TrialQuestionnaireDto res = userService.checkTrialQuestionnaire(email);

    return ResponseEntity.ok(res);
  }

  @PostMapping(path = "/trial-upgrade")
  public ResponseEntity trialUpgrade(@RequestBody TrialUpgradeDto upgradeDto, HttpServletRequest request) throws UserNotFoundException {
    String unsubscribeDto = userService.trialUpgrade(upgradeDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping(path = "/check-coupon")
  public ResponseEntity checkCoupon(@RequestBody String coupon, HttpServletRequest request) {
    JSONObject response = userService.checkCoupon(coupon.replace("coupon=", ""));
    return ResponseEntity.ok(response.toMap());
  }

  @RequestMapping(value = "/limits", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public ResponseEntity getUserLimits(@RequestParam(required = false) Long userId, @RequestParam(required = false) String type, HttpServletRequest request) throws UserNotFoundException {
    Long id = userId;
    
    if (id == null || id.equals(0L)) {
      id = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION)).getUserId();
    }

    if (id == null) {
      throw new UserNotFoundException("User with id: " + userId + " not found");
    }

    if (type != null && type.length() > 0) {
      UserLimit limit = userLimitService.getSpecificLimit(userId, type);
      return ResponseEntity.ok(limit);
    }

    List<UserLimit> limits = userLimitService.getAllLimits(id);
    return ResponseEntity.ok(limits);
  }

  @Scheduled(cron = "${co.polarpublishing.data.update.schedule.wpdata.cron-expression}")
  public void updateUserDataFromWp() throws UserNotFoundException {
    userService.updateUserWpData();
  }

  @GetMapping(path = "/is-admin")
  public ResponseEntity isAdmin(HttpServletRequest request) {
    Long userId = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION)).getUserId();
    return ResponseEntity.ok(userService.isAdmin(userId));
  }

  @Scheduled(cron = "${co.polarpublishing.data.update.schedule.unsubscribe-reasons}")
  @GetMapping(path = "/unsubscribe-reasons")
  public void unsubscribeReasons() {
    userService.sendUnsubscribeReasons(cancellationMail);
  }
}