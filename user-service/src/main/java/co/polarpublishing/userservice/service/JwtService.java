package co.polarpublishing.userservice.service;

import co.polarpublishing.userservice.dto.TokenInfo;

/**
 * The {@code JwtService} interface for parsing and generating access tokens.
 */
public interface JwtService {

	/**
	 * Generates a new access token by {@code tokenInfo}.
	 *
	 * @param tokenInfo {@code TokenInfo} value specifying the info to be saved in the token.
	 * @return the access token.
	 */
	public String generateAccessToken(TokenInfo tokenInfo, String userPlan);

	/**
	 * Generates a new refresh token by {@code tokenInfo} used to obtain access tokens.
	 * Refresh tokens are issued to the client by the authorization server and are used to obtain a new
	 * access token when the current access token becomes invalid or expires, or to obtain additional
	 * access tokens with identical or narrower scope.
	 *
	 * @param tokenInfo {@code TokenInfo} value specifying the info to be saved in the token.
	 * @return the access token.
	 */
	String generateRefreshToken(TokenInfo tokenInfo, String userPlan);


	/**
	 * Returns the {@code tokenInfo} value from the {@code token} or {@code null} if not present.
	 *
	 * @param token {@code String} value specifying the unique identifier of the user.
	 * @return the {@code tokenInfo} value or {@code null} if not present.
	 */
	TokenInfo getAccessTokenInfo(String token);

	/**
	 * Returns the {@code tokenInfo} value from the {@code token} or {@code null} if not present.
	 *
	 * @param token {@code String} value specifying the unique identifier of the user.
	 * @return the {@code email} value or {@code null} if not present.
	 */
	TokenInfo getRefreshTokenInfo(String token);

}