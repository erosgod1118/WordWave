package co.polarpublishing.common.util;

import co.polarpublishing.common.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
// import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

@Component
// @Slf4j
public class JwtUtil {

  private final String jwtSecret;

  public JwtUtil(@Value("${jwt.access.token.secret}") String jwtSecret) {
    this.jwtSecret = jwtSecret;
  }

  public UserDto getUser(String token) {
    String subject = Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token).getBody()
        .getSubject();
    String[] userInfos = subject.split("::");

    return UserDto.builder().email(userInfos[0]).userId(Long.parseLong(userInfos[1])).build();
  }

  public String getSubscriptionPlan(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(this.jwtSecret)
        .parseClaimsJws(token)
        .getBody();

    return claims.get("plan", String.class);
  }

  public boolean hasProPlan(String token) {
    return containsIgnoreCase(this.getSubscriptionPlan(token), "Pro");
  }

  public boolean hasStarterPlan(String token) {
    return containsIgnoreCase(this.getSubscriptionPlan(token), "Starter");
  }

  public Long getUserId(String token) {
    return this.getUser(token).getUserId();
  }

}
