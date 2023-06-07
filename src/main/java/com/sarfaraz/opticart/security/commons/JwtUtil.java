package com.sarfaraz.opticart.security.commons;

import com.sarfaraz.opticart.security.commons.model.JwtUser;
import com.sarfaraz.opticart.security.commons.token.AccessToken;
import com.sarfaraz.opticart.security.commons.token.AuthToken;
import com.sarfaraz.opticart.security.commons.token.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.sarfaraz.opticart.security.commons.constants.JwtConstants.*;

public class JwtUtil {

    private JwtUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static AccessToken generateToken(JwtUser jwtUser, JwtProperties jwtProperties) {
        Instant now = Instant.now();
        Instant expireTime = now.plusSeconds(jwtProperties.getTokenExpirationSeconds().getSeconds());
        jwtUser.getUnmodifiableScopes().add(ROLE_ACCESS_TOKEN);

        Claims claims = Jwts.claims().setSubject(jwtUser.getUserId());
        claims.put(SCOPES_KEY_NAME, jwtUser.getUnmodifiableScopes());
        return new AccessToken(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuer(jwtProperties.getTokenIssuer())
                        .setSubject(jwtUser.getUserId())
                        .setIssuedAt(Date.from(now))
                        .setExpiration(Date.from(expireTime))
                        .signWith(SignatureAlgorithm.RS512, jwtProperties.getPrivateKey())
                        .compact(),
                LocalDateTime.ofInstant(expireTime, ZoneId.of("UTC")));
    }

    public static RefreshToken generateRefreshToken(JwtUser jwtUser, JwtProperties jwtProperties) {
        Instant now = Instant.now();
        Instant expireTime = now.plusSeconds(jwtProperties.getRefreshTokenExpSeconds().getSeconds());
        jwtUser.getUnmodifiableScopes().add(ROLE_REFRESH_TOKEN);

        Claims claims = Jwts.claims().setSubject(jwtUser.getUserId());
        claims.put(SCOPES_KEY_NAME, jwtUser.getUnmodifiableScopes());

        return new RefreshToken(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuer(jwtProperties.getTokenIssuer())
                        .setSubject(jwtUser.getUserId())
                        .setIssuedAt(Date.from(now))
                        .setExpiration(Date.from(expireTime))
                        .signWith(SignatureAlgorithm.RS512, jwtProperties.getPrivateKey())
                        .compact(),
                LocalDateTime.ofInstant(expireTime, ZoneId.of("UTC")));
    }

    public static JwtUser validateAndExtractToken(String token, JwtProperties jwtProperties) {
        return validateAndExtractToken(token, jwtProperties, ROLE_ACCESS_TOKEN);
    }

    public static JwtUser validateAndExtractRefreshToken(String token, JwtProperties jwtProperties) {
        return validateAndExtractToken(token, jwtProperties, ROLE_REFRESH_TOKEN);
    }

    public static AccessToken validateToken(String token, JwtProperties jwtProperties) {
        return (AccessToken) validateToken(token, jwtProperties, ROLE_ACCESS_TOKEN);
    }

    public static RefreshToken validateRefreshToken(String token, JwtProperties jwtProperties) {
        return (RefreshToken) validateToken(token, jwtProperties, ROLE_REFRESH_TOKEN);
    }

    private static JwtUser validateAndExtractToken(String token, JwtProperties jwtProperties, String role) {
        Claims claims = getClaims(token, jwtProperties);
        Set<String> scopes = validateAndGetScopes(role, claims);
        return new JwtUser(
                Optional.of(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("Invalid claims")),
                scopes
        );
    }

    private static Set<String> validateAndGetScopes(String role, Claims claims) {
        Set<String> scopes = new HashSet<>((List) claims.get(SCOPES_KEY_NAME));
        if (!scopes.contains(role)) throw new IllegalArgumentException("no access scope");
        return scopes;
    }

    public static Claims getClaims(String token, JwtProperties jwtProperties) {
        return Jwts.parser().setSigningKey(jwtProperties.getPublicKey()).parseClaimsJws(token).getBody();
    }

    public static AuthToken validateToken(String token, JwtProperties jwtProperties, String role) {
        Claims claims = getClaims(token, jwtProperties);
        validateAndGetScopes(role, claims);

        if (ROLE_ACCESS_TOKEN.equals(role)) {
            return new AccessToken(token, LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.of("UTC")));
        } else if (ROLE_REFRESH_TOKEN.equals(role)) {
            return new RefreshToken(token, LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.of("UTC")));
        } else {
            throw new IllegalArgumentException("Invalid token ");
        }
    }

}
