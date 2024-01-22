package co.polarpublishing.userservice.service.impl;

import co.polarpublishing.userservice.dto.TokenInfo;
import co.polarpublishing.userservice.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

	@Value("${jwt.access.token.expiration}")
	private Long accessTokenExpiration;

	@Value("${jwt.refresh.token.expiration}")
	private Long refreshTokenExpiration;

	@Value("${jwt.access.token.secret}")
	private String jwtAccessTokenSecret;

	@Value("${jwt.refresh.token.secret}")
	private String jwtRefreshTokenSecret;

	@Override
	public String generateAccessToken(TokenInfo tokenInfo, String userPlan) {
		return Jwts
						.builder()
						.setSubject(tokenInfo.toString())
						.claim("plan", userPlan)
						.setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
						.signWith(SignatureAlgorithm.HS512, jwtAccessTokenSecret)
						.compact();
	}

	@Override
	public String generateRefreshToken(TokenInfo tokenInfo, String userPlan) {
		return Jwts
						.builder()
						.setSubject(tokenInfo.toString())
						.claim("plan", userPlan)
						.setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
						.signWith(SignatureAlgorithm.HS512, jwtRefreshTokenSecret)
						.compact();
	}

	public TokenInfo getAccessTokenInfo(String token) {
		Claims claims = Jwts
						.parser()
						.setSigningKey(jwtAccessTokenSecret)
						.parseClaimsJws(token)
						.getBody();
		String[] subject = claims.getSubject().split("::");
		long userId = Long.parseLong(subject[1]);
		String plan = claims.get("plan", String.class);

		return TokenInfo
						.builder()
						.userId(userId)
						.email(subject[0])
						.plan(plan)
						.build();
	}

	public TokenInfo getRefreshTokenInfo(String token) {
		String[] subject = Jwts
						.parser()
						.setSigningKey(jwtRefreshTokenSecret)
						.parseClaimsJws(token)
						.getBody()
						.getSubject()
						.split("::");
		long userId = Long.parseLong(subject[1]);

		return TokenInfo
						.builder()
						.userId(userId)
						.email(subject[0])
						.build();
	}

}
