package co.polarpublishing.userservice.controller;

import co.polarpublishing.common.dto.ErrorResponse;
import co.polarpublishing.common.util.DateAndTimeUtil;
import co.polarpublishing.common.dto.UserDto;
import co.polarpublishing.common.dto.keys.AmazonKeysDto;
import co.polarpublishing.userservice.dto.TokenInfo;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.exception.KeysForSuchMarketplaceAlreadyExistsException;
import co.polarpublishing.userservice.exception.KeysNotFoundException;
import co.polarpublishing.userservice.exception.UserNotFoundException;
import co.polarpublishing.userservice.service.impl.AmazonKeysService;
import co.polarpublishing.userservice.service.JwtService;
import co.polarpublishing.userservice.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/keys")
@Api(tags = "keys-api", description = "Amazon Keys Service REST APIs")
@RequiredArgsConstructor
public class AmazonKeysController {

	private final UserService userService;
	private final AmazonKeysService amazonKeysService;
	private final JwtService jwtService;

	@ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success. Returns updated user object.")
	})
	@RequestMapping(value = "/marketplaces", method = RequestMethod.GET)
	public ResponseEntity getListOfMarketplaces() {
		return ResponseEntity.ok(amazonKeysService.getListOfMarketplaces());
	}

	@ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success. Returns updated user object.")
	})
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity getListOfAmazonKeysForUser(HttpServletRequest request) {
		try {
			TokenInfo tokenInfo = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
			User user = userService.findModelByEmail(tokenInfo.getEmail());

			return ResponseEntity.ok(amazonKeysService.getKeysForUser(user));
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

	@ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success. Returns updated user object."),
		@ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
		@ApiResponse(code = 404, message = "Failure. Indicates the user does not exist"),
		@ApiResponse(code = 409, message = "Failure. Indicates the keys for this user and marketplace are already exists")
	})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity addKeys(@Valid @RequestBody AmazonKeysDto dto, HttpServletRequest request) {
		try {
			TokenInfo tokenInfo = jwtService.getAccessTokenInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
			UserDto userDto = userService.findByEmail(tokenInfo.getEmail());

			return ResponseEntity.ok(amazonKeysService.addKeys(userDto.getUserId(), dto));
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
		} catch (KeysForSuchMarketplaceAlreadyExistsException e) {
				return ResponseEntity
								.status(HttpStatus.BAD_REQUEST)
								.body(
									ErrorResponse
										.builder()
										.code(e.getCode())
										.message(e.getMessage())
										.status(HttpStatus.BAD_REQUEST.value())
										.timestamp(DateAndTimeUtil.getCurrentEpochTime())
										.build());
		}
	}

	@ApiImplicitParam(name = "Authorization", dataType = "string", paramType = "header", required = true)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success. Returns updated user object."),
		@ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
		@ApiResponse(code = 404, message = "Failure. Indicates the keys does not exist"),
	})
	@RequestMapping(value = "/{keysId}", method = RequestMethod.PUT)
	public ResponseEntity updateKeys(@PathVariable Long keysId, @RequestBody AmazonKeysDto dto) {
		try {
			return ResponseEntity.ok(amazonKeysService.updateKeys(keysId, dto));
		} catch (KeysNotFoundException ex) {
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
		@ApiResponse(code = 200, message = "Success. Returns updated user object."),
		@ApiResponse(code = 400, message = "Failure. Indicates the request body is invalid."),
		@ApiResponse(code = 404, message = "Failure. Indicates the keys does not exist"),
	})
	@RequestMapping(value = "/{keysId}", method = RequestMethod.DELETE)
	public ResponseEntity deleteKeys(@PathVariable Long keysId) {
		try {
			amazonKeysService.deleteKeys(keysId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(HttpStatus.NO_CONTENT);
		} catch (KeysNotFoundException ex) {
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

}