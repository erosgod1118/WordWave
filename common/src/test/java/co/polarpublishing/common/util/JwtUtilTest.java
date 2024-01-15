package co.polarpublishing.common.util;

import co.polarpublishing.common.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JwtUtilTest {

  private JwtUtil jwtUtil;
  private String token;
  private String secret;

  @Before
  public void setUp() throws Exception {
    this.secret = "jwtSecret";
    this.token = Jwts.builder()
        .setSubject(String.format("%s::%s", "name@email.com", "1"))
        .setExpiration(new Date(System.currentTimeMillis() + 30000))
        .signWith(SignatureAlgorithm.HS512, this.secret)
        .compact();
    this.jwtUtil = new JwtUtil(this.secret);
  }

  @Test(expected = SignatureException.class)
  public void testGetUser_WithInvalidSecret() {
    this.jwtUtil = new JwtUtil("randomsecrent");
    this.jwtUtil.getUser(this.token);
  }

  @Test(expected = MalformedJwtException.class)
  public void testGetUser_WithInvalidTokenFormat() {
    this.jwtUtil.getUser("randomtoken");
  }

  @Test(expected = SignatureException.class)
  public void testGetUser_WithInvalidToken() {
    this.jwtUtil.getUser(
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
  }

  @Test
  public void testGetUser() {
    UserDto user = this.jwtUtil.getUser(this.token);

    Assert.assertNotNull(user);
    Assert.assertEquals("name@email.com", user.getEmail());
    Assert.assertEquals(new Long(1), user.getUserId());
  }

}
