package co.polarpublishing.userservice.controller;

import co.polarpublishing.userservice.dto.ConfirmAccountDto;
import co.polarpublishing.userservice.dto.SendConfirmationDto;
import co.polarpublishing.userservice.exception.AccountAlreadyConfirmedException;
import co.polarpublishing.userservice.service.UserLimitService;
import co.polarpublishing.common.dto.ErrorResponse;
import co.polarpublishing.common.dto.MailDto;
import co.polarpublishing.common.dto.UserDto;
import co.polarpublishing.common.exception.CustomException;
import co.polarpublishing.common.util.DateAndTimeUtil;
import co.polarpublishing.userservice.dto.ForgotPasswordDto;
import co.polarpublishing.userservice.dto.TokenDto;
import co.polarpublishing.userservice.dto.ResetPasswordDto;
import co.polarpublishing.userservice.dto.SignInDto;
import co.polarpublishing.userservice.dto.SignUpDto;
import co.polarpublishing.userservice.dto.TokenInfo;
import co.polarpublishing.userservice.dto.UserWpDataDto;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.exception.UserAlreadyExistsException;
import co.polarpublishing.userservice.exception.UserNotFoundException;
import co.polarpublishing.userservice.service.AuthService;
import co.polarpublishing.userservice.service.JwtService;
import co.polarpublishing.userservice.service.MailService;
import co.polarpublishing.userservice.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@Api(tags = "auth-api", description = "Auth Service REST APIs")
@RequiredArgsConstructor
public class AuthController {

	private static final String USER_NOT_EXISTS_MSG = "User with such email and password does not exist.";
	private static final String CONFIRMATION_TOKEN_PLACEHOLDER = "[CONFIRMATION_TOKEN_PLACEHOLDER]";
	private static final String RESET_TOKEN_PLACEHOLDER = "[RESET_TOKEN_PLACEHOLDER]";
	private static final String NAME_PLACEHOLDER = "[NAME_PLACEHOLDER]";
	private static final String TRIAL = "Trial";
	private static final Set<String> WHITELIST_ENDPOINTS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
		"/user-service/api/v1/auth/sign-in",
		"/user-service/api/v1/auth/sign-up",
		"/user-service/api/v1/auth/confirm",
		"/user-service/api/v1/auth/refresh-token",
		"/user-service/api/v1/auth/forgot-password",
		"/user-service/api/v1/auth/reset-password",
		"/user-service/api/v1/auth/send-confirmation",
		"/book-tracker-service/api/v1/landing-page/audience",
		"/book-tracker-service/api/v1/landing-page/auth/aweber",
		"/book-tracker-service/api/v1/landing-page/auth/mailchimp",
		"/book-tracker-service/api/v1/landing-page/auth/aweber/callback",
		"/book-tracker-service/api/v1/landing-page/auth/mailchimp/callback"
	)));
	private static final Map<String, String> LIMITED_ENDPOINTS = Stream.of(new String[][]{
		{"POST:/book-tracker-service/api/v1/niche-finder", "NICHE_FINDER_SEARCHES"},
		{"POST:/book-tracker-service/api/v1/tracking-books", "BOOK_TRACKER_BOOKS"},
		{"GET:/administration-service/api/v1/configurations/limit", "CHROME_EXTENSION"},
		{"GET:/book-tracker-service/api/v1/suggestions/limit", "KEYWORD_GENERATOR_SEARCHES"},
		{"POST:/keyword-research-service/api/v1/keyword-research-updates/start", "KEYWORD_RESEARCH_SEARCHES"},
		{"GET:/category-explorer-service/api/v1/categoryexplorer/subcategories/", "CATEGORY_EXPLORER_SEARCHES"}
	}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	private final AuthService authService;
	private final UserService userService;
	private final JwtService jwtService;
	private final MailService mailService;
	private final UserLimitService userLimitService;

	@Value("classpath:mail/templates/reset-password.html")
	Resource resetResourceFile;

	@Value("classpath:mail/templates/account-activation.html")
	Resource activationResourceFile;

	@Value("classpath:mail/templates/account-activation-confirmation.html")
	Resource confirmActivationResourceFile;

	@Value("classpath:mail/templates/reset-password-confirmation.html")
	Resource confirmResetResourceFile;

	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success. Saves and returns a new user."),
		@ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid.")
	})
	@RequestMapping(value = "/sign-in", method = RequestMethod.POST)
	public ResponseEntity signIn(@Valid @RequestBody SignInDto signInDto) {
		try {
			//check latest WP data
			UserWpDataDto wpData = userService.getUserWpData(signInDto.getEmail());

			String subscriptionStatus = wpData.getUserSubscriptionStatus();
			if (subscriptionStatus == null || subscriptionStatus.equals("0")) {
				return ResponseEntity
								.status(HttpStatus.UNAUTHORIZED)
								.body(
									ErrorResponse
										.builder()
										.code(401)
										.message("No subscription")
										.status(HttpStatus.UNAUTHORIZED.value())
										.timestamp(DateAndTimeUtil.getCurrentEpochTime())
										.build()
								);
			}

			UserDto signInUser = authService.signIn(signInDto.getEmail(), signInDto.getPassword());
			TokenInfo tokenInfo = TokenInfo
							.builder()
							.userId(signInUser.getUserId())
							.email(signInUser.getEmail())
							.build();
			String accessToken = jwtService.generateAccessToken(tokenInfo, signInUser.getUserCurrentPlan());
			String refreshToken = jwtService.generateRefreshToken(tokenInfo, signInUser.getUserCurrentPlan());

			signInUser.setAccessToken(accessToken);
			signInUser.setRefreshToken(refreshToken);

			return ResponseEntity.ok(signInUser);
		} catch (UserNotFoundException ex) {
			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
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

	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success. Returns a new access token."),
		@ApiResponse(code = 400, message = "Failure. Indicates the request is invalid.")
	})
	@RequestMapping(value = "/refresh-token", method = RequestMethod.POST)
	public ResponseEntity refreshToken(@Valid @RequestBody TokenDto refreshToken) {
		try {
			//check latest WP data
			TokenInfo tokenInfo = jwtService.getRefreshTokenInfo(refreshToken.getJwt());
			UserWpDataDto wpData = userService.getUserWpData(tokenInfo.getEmail());

			String subscriptionStatus = wpData.getUserSubscriptionStatus();
			if (subscriptionStatus == null || subscriptionStatus.equals("0")) {
				ErrorResponse errorResponse = ErrorResponse
								.builder()
								.message("No subscription")
								.code(HttpStatus.BAD_REQUEST.value())
								.status(HttpStatus.BAD_REQUEST.value())
								.timestamp(DateAndTimeUtil.getCurrentEpochTime())
								.build();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
			}

			TokenDto accessToken = TokenDto
															.builder()
															.jwt(jwtService.generateAccessToken(tokenInfo, wpData.getUserCurrentPlan()))
															.build();
			return ResponseEntity.ok(accessToken);
		} catch (UserNotFoundException ex) {
			return ResponseEntity
							.status(HttpStatus.BAD_REQUEST)
							.body(
								ErrorResponse
									.builder()
									.code(ex.getCode())
									.message(ex.getMessage())
									.status(HttpStatus.BAD_REQUEST.value())
									.timestamp(DateAndTimeUtil.getCurrentEpochTime())
									.build()
							);
		} catch (Exception e) {
				return ResponseEntity
								.status(HttpStatus.UNAUTHORIZED)
								.body(
									ErrorResponse
										.builder()
										.code(HttpStatus.UNAUTHORIZED.value())
										.message(e.getMessage())
										.status(HttpStatus.UNAUTHORIZED.value())
										.timestamp(DateAndTimeUtil.getCurrentEpochTime())
										.build()
								);
		}
	}

	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success. Saves and returns a new user."),
		@ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
		@ApiResponse(code = 409, message = "Failure. Indicates the user already exists with such email address.")
	})
	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
	public ResponseEntity signUp(@Valid @RequestBody SignUpDto signUpDto) {
		try {
			UserDto savedUser;

			if (TRIAL.equals(signUpDto.getUserCurrentPlan())) {
				String confirmationToken = UUID.randomUUID().toString();
				signUpDto.setConfirmationToken(confirmationToken);
				
				savedUser = authService.signUp(signUpDto);
				userLimitService.insertUserLimits(savedUser.getUserId());

				String template = "";
				try {
					template = IOUtils
									.toString(activationResourceFile.getInputStream(), StandardCharsets.UTF_8)
									.replace(NAME_PLACEHOLDER, savedUser.getFirstName())
									.replace(CONFIRMATION_TOKEN_PLACEHOLDER, confirmationToken);
				} catch (IOException e) {
					log.error(e.getMessage());
				}

				mailService
					.sendHtml(
						MailDto
							.builder()
							.subject("Confirm email address to start your FREE trial")
							.message(template)
							.to(savedUser.getEmail())
							.build()
					);
			} else {
				savedUser = authService.signUp(signUpDto);
			}

			TokenInfo tokenInfo = TokenInfo
							.builder()
							.userId(savedUser.getUserId())
							.email(savedUser.getEmail())
							.build();

			savedUser.setAccessToken(jwtService.generateAccessToken(tokenInfo, savedUser.getUserCurrentPlan()));
			savedUser.setRefreshToken(jwtService.generateRefreshToken(tokenInfo, savedUser.getUserCurrentPlan()));

			return ResponseEntity.ok(savedUser);
		} catch (UserAlreadyExistsException ex) {
				return ResponseEntity
								.status(HttpStatus.BAD_REQUEST)
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
		@ApiResponse(code = 204, message = "Success. Indicates the token is valid.")
	})
	@RequestMapping(value = "/token-status", method = RequestMethod.GET)
	public ResponseEntity tokenStatus(HttpServletRequest request) {
			try {
				String email = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION)).getEmail();
				
				userService.findByEmail(email);

				return ResponseEntity.noContent().build();
			} catch (UserNotFoundException ex) {
				return ResponseEntity
								.status(HttpStatus.BAD_REQUEST)
								.body(
									ErrorResponse
										.builder()
										.code(ex.getCode())
										.message(ex.getMessage())
										.status(HttpStatus.BAD_REQUEST.value())
										.timestamp(DateAndTimeUtil.getCurrentEpochTime())
										.build()
								);
			} catch (Exception e) {
				return ResponseEntity
								.status(HttpStatus.UNAUTHORIZED)
								.body(
									ErrorResponse
										.builder()
										.code(HttpStatus.UNAUTHORIZED.value())
										.message(e.getMessage())
										.status(HttpStatus.UNAUTHORIZED.value())
										.timestamp(DateAndTimeUtil.getCurrentEpochTime())
										.build()
								);
			}
	}

	@ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
	@ApiResponses(value = {
		@ApiResponse(code = 204, message = "Success. Indicates the access is authorized."),
		@ApiResponse(code = 401, message = "Failure. Indicates the access is not authorized."),
		@ApiResponse(code = 403, message = "Failure. Indicates the account limits are exceeded."),
	})
	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public ResponseEntity authorize(HttpServletRequest request) {
		try {
			if (HttpMethod.OPTIONS.matches(request.getMethod())) {
				log.debug("Successfully authorized OPTIONS request.");

				return ResponseEntity.noContent().build();
			}

			String originalUrl = request.getHeader("x-auth-request-redirect");
			if (originalUrl != null && WHITELIST_ENDPOINTS.contains(originalUrl.split("\\?")[0])) {
				log.debug("Successfully authorized request on ignored path: {}", originalUrl);

				return ResponseEntity.noContent().build();
			}

			Cookie[] cookies = request.getCookies();
			Optional<String> token = Arrays
							.stream(cookies != null ? cookies : new Cookie[0])
							.filter(c -> "auth_token".equalsIgnoreCase(c.getName()))
							.map(Cookie::getValue)
							.findFirst();
			String authToken = token.orElse(request.getHeader(HttpHeaders.AUTHORIZATION));
			TokenInfo tokenInfo = jwtService.getAccessTokenInfo(authToken);

			String endpoint = request.getHeader("x-original-method") + ":" + originalUrl.split("\\?")[0];
			log.info("Endpoint: {}", endpoint);

			String type = LIMITED_ENDPOINTS.get(endpoint);
			if (type != null) {
				boolean exceeded = userLimitService.exceeds(endpoint, type, tokenInfo.getUserId(), tokenInfo.getPlan());
				if (exceeded) {
					ErrorResponse error = ErrorResponse
									.builder()
									.code(4290)
									.status(4290)
									.timestamp(DateAndTimeUtil.getCurrentEpochTime())
									.message("Account limits exceeded. Please upgrade your plan.")
									.build();

					return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
				}
			}

			log.debug("Successfully authorized request: {}", originalUrl);
			return ResponseEntity.noContent().build();
		} catch (Throwable e) {
			log.warn("Failed to authorize request!");

			Collections
				.list(request.getHeaderNames())
				.stream()
				.filter(h -> h.startsWith("x-"))
				.forEach(h -> System.out.println(h + ": " + request.getHeader(h)));

			int errCode = e instanceof CustomException ? ((CustomException) e).getCode() : -1;
			ErrorResponse error = ErrorResponse
							.builder()
							.code(errCode)
							.message(e.getMessage())
							.status(HttpStatus.UNAUTHORIZED.value())
							.timestamp(DateAndTimeUtil.getCurrentEpochTime())
							.build();

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}
	}

	@ApiResponses(value = {
		@ApiResponse(code = 204, message = "Success. Indicates the reset password link is sent by email."),
		@ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
		@ApiResponse(code = 404, message = "Success. Indicates the user does not exist.")
	})
	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public ResponseEntity forgotPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto) {
		try {
			User user = userService.findModelByEmail(forgotPasswordDto.getEmail());
			String resetToken = UUID.randomUUID().toString();

			user.setResetToken(resetToken);
			userService.update(user);

			String template = "";
			try {
				template = IOUtils
								.toString(resetResourceFile.getInputStream(), StandardCharsets.UTF_8)
								.replace(NAME_PLACEHOLDER, user.getFirstName())
								.replace(RESET_TOKEN_PLACEHOLDER, resetToken);
			} catch (IOException e) {
				log.error(e.getMessage());
			}

			mailService.sendHtml(
					MailDto
						.builder()
						.subject("BookBeam Account Password Reset.")
						.message(template)
						.to(user.getEmail())
						.build()
			);

			return ResponseEntity.noContent().build();
		} catch (UserNotFoundException ex) {
			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
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

	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success. Indicates the reset password was successfully finished."),
		@ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
		@ApiResponse(code = 404, message = "Success. Indicates the user does not exist.")
	})
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
		try {
			UserDto userDto = userService.resetPassword(resetPasswordDto);
			String template = "";
			try {
				template = IOUtils
									.toString(confirmResetResourceFile.getInputStream(), StandardCharsets.UTF_8)
									.replace(NAME_PLACEHOLDER, userDto.getFirstName());
			} catch (IOException e) {
				log.error(e.getMessage());
			}

			mailService.sendHtml(
				MailDto
					.builder()
					.subject("You successfully changed your password.")
					.message(template)
					.to(userDto.getEmail())
					.build()
			);

			return ResponseEntity.ok(userDto);
		} catch (UserNotFoundException ex) {
			return ResponseEntity
							.status(HttpStatus.BAD_REQUEST)
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

	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success. Indicates the account activation was successfully finished."),
		@ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
		@ApiResponse(code = 404, message = "Success. Indicates the user does not exist.")
	})
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public ResponseEntity confirmAccount(@Valid @RequestBody ConfirmAccountDto confirmAccountDto) {
		try {
			UserDto userDto = userService.confirmAccount(confirmAccountDto);
			return ResponseEntity.ok(userDto);
		} catch (UserNotFoundException | AccountAlreadyConfirmedException ex) {
			return ResponseEntity
							.status(HttpStatus.BAD_REQUEST)
							.body(
								ErrorResponse
									.builder()
									.code(ex.getCode())
									.message(ex.getMessage())
									.status(ex.getCode())
									.timestamp(DateAndTimeUtil.getCurrentEpochTime())
									.build()
							);
		}
	}

	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success. Indicates the account activation was successfully finished."),
		@ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
		@ApiResponse(code = 404, message = "Success. Indicates the user does not exist.")
	})
	@RequestMapping(value = "/send-confirmation", method = RequestMethod.POST)
	public ResponseEntity sendConfirmation(@Valid @RequestBody SendConfirmationDto sendConfirmation) {
		try {
			User user = userService.findModelByEmail(sendConfirmation.getEmail());
			if (user.getUserSubscriptionStatus().equals("1")) {
				throw new AccountAlreadyConfirmedException();
			}

			user.setConfirmationToken( UUID.randomUUID().toString());
			user = userService.update(user);

			String template = IOUtils
									.toString(activationResourceFile.getInputStream(), StandardCharsets.UTF_8)
									.replace(NAME_PLACEHOLDER, user.getFirstName())
									.replace(CONFIRMATION_TOKEN_PLACEHOLDER, user.getConfirmationToken());

			mailService.sendHtml(
				MailDto
					.builder()
					.subject("Confirm email address to start your FREE trial")
					.message(template)
					.to(user.getEmail())
					.build()
			);

			return ResponseEntity.ok().build();
		} catch (UserNotFoundException | AccountAlreadyConfirmedException ex) {
			return ResponseEntity
							.status(HttpStatus.BAD_REQUEST)
							.body(
								ErrorResponse
									.builder()
									.code(ex.getCode())
									.message(ex.getMessage())
									.status(ex.getCode())
									.timestamp(DateAndTimeUtil.getCurrentEpochTime())
									.build()
							);
		} catch (IOException e) {
			ErrorResponse errorResponse = ErrorResponse
							.builder()
							.message(e.getMessage())
							.timestamp(DateAndTimeUtil.getCurrentEpochTime())
							.build();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

}