package com.example.jangboo.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.example.jangboo.auth.controller.dto.Info.CurrentUserInfo;
import com.example.jangboo.auth.controller.dto.response.JwtToken;
import com.example.jangboo.auth.domain.user.CustomUserDetails;

@Component
public class JwtTokenProvider {
	public static final String BEARER_TYPE = "Bearer";
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String REFRESH_HEADER = "Refresh";
	public static final String BEARER_PREFIX = "Bearer ";

	@Value("${jwt.secret_key}")
	private String encodedSecretKey;

	@Value("${jwt.access_token.valid_time}")
	private Long accessTokenValidTime;

	@Value("${jwt.refresh_token.valid_time}")
	private Long refreshTokenValidTime;

	public JwtToken generateToken(CustomUserDetails customUserDetails) {
		String accessToken = generateAccessToken(customUserDetails);
		String refreshToken = generateRefreshToken(customUserDetails);
		return new JwtToken(BEARER_TYPE,accessToken,refreshToken);
	}

	public CurrentUserInfo extractUserInfo(String token) {
		Long userId = Long.parseLong(extractClaim(token, Claims::getSubject));
		Long deptId = extractClaim(token, claims -> claims.get("deptId", Long.class));
		return new CurrentUserInfo(userId, deptId);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
			.setSigningKey(encodedSecretKey)
			.parseClaimsJws(token)
			.getBody();
	}

	public String generateAccessToken(CustomUserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();

		claims.put("deptId",userDetails.getDeptId());
		return createToken(claims, userDetails.getUsername(),accessTokenValidTime);
	}

	public String generateRefreshToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername(),refreshTokenValidTime);
	}

	public String createToken(Map<String, Object> claims, String userId, Long validTime) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validTime);

		return Jwts.builder()
			.setSubject(userId)
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(getSecretKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	private Key getSecretKey() {
		byte[] keyBytes = Base64.getDecoder().decode(encodedSecretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	// 토큰 만료 여부 확인
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}
