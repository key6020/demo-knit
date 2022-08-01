package com.task.demo2.config.jwt;

import com.task.demo2.repository.ExpiredTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;
    private final ExpiredTokenRepository expiredTokenRepository;

    public String createAccessToken(String email) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 60 * 30 * 1000);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setHeader(headers)
                .setClaims(claims)
                .setIssuer("demo")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256).compact();
    }

    public String createRefreshToken(String email) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000L);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setHeader(headers)
                .setClaims(claims)
                .setIssuer("demo")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256).compact();
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            if (expiredTokenRepository.existsByAccessToken(accessToken)) {
                throw new IllegalArgumentException("Invalid Token.");
            }
            return (parseToken(accessToken).getExpiration().before(new Date()));
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw e;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw e;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            return (parseToken(refreshToken).getExpiration().before(new Date()));
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw e;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw e;
        }
    }

    public String getUserEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getBody().get("email").toString();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getBody();
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
