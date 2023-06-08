package com.sarfaraz.opticart.security.auth.jwt;

import com.google.common.collect.ImmutableSet;
import com.sarfaraz.opticart.security.auth.context.AppUserContext;
import com.sarfaraz.opticart.security.auth.jwt.extractor.TokenExtractor;
import com.sarfaraz.opticart.security.commons.JwtProperties;
import com.sarfaraz.opticart.security.commons.model.JwtUser;
import com.sarfaraz.opticart.security.commons.token.AccessToken;
import com.sarfaraz.opticart.security.commons.token.RefreshToken;
import com.sarfaraz.opticart.security.dto.AuthTokenDto;
import com.sarfaraz.opticart.security.enums.SignupStatus;
import com.sarfaraz.opticart.security.service.TokenService;
import com.sarfaraz.opticart.security.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.sarfaraz.opticart.security.commons.JwtUtil.*;
import static com.sarfaraz.opticart.security.enums.SignInStatus.TAMPERED_REQUEST;

@Component
public class JwtFacade {
    private final TokenService tokenService;
    private final UserService userService;

    private final JwtProperties jwtProperties;

    private final TokenExtractor tokenExtractor;


    public JwtFacade(TokenService tokenService, UserService userService, JwtProperties jwtProperties, TokenExtractor tokenExtractor) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.jwtProperties = jwtProperties;
        this.tokenExtractor = tokenExtractor;
    }

    public SignupStatus validateEmail(String email) {
        return userService.validateEmail(email);
    }

    public SignupStatus validatePhone(String phoneNumber) {
        return userService.validatePhone(phoneNumber);
    }


    public AuthTokenDto refreshToken(String refreshToken) {
        String bearerToken = tokenExtractor.extract(refreshToken);
        if (!tokenService.isValidRefreshToken(bearerToken))
            throw new UnsupportedOperationException();

        JwtUser jwtUser = validateAndExtractRefreshToken(bearerToken, jwtProperties);
        String userId = jwtUser.getUserId();
        boolean user = userService.existsById(userId);
        if (!user)
            throw new IllegalArgumentException(TAMPERED_REQUEST.name());

        AccessToken newAccessToken = generateToken(jwtUser, jwtProperties);
        RefreshToken newRefreshToken = generateRefreshToken(jwtUser, jwtProperties);
        tokenService.updateTokensByRefreshToken(newAccessToken, newRefreshToken, bearerToken);
        return AuthTokenDto.builder()
                .accessToken(newAccessToken.getToken())
                .refreshToken(newRefreshToken.getToken())
                .tokenType(jwtProperties.getActiveTokenType())
                .build();
    }


    public ImmutableSet<String> getAuthorities(String userId) {
        return userService.getAuthorities(userId);
    }

    public AuthTokenDto getTokens(JwtUser jwtUser) {
        return tokenService
                .saveTokens(
                        generateToken(jwtUser, jwtProperties),
                        generateRefreshToken(jwtUser, jwtProperties),
                        jwtProperties.getActiveTokenType(),
                        jwtUser.getUserId());
    }

    public boolean isValidAccessToken(String token) {
        return tokenService.isValidAccessToken(token);
    }

    public boolean logMeOutFromAllDevices() {
        return tokenService.deleteByUserId(getMyUserId());
    }

    public boolean logMeOutFromAllDevices(String userId) {
        return tokenService.deleteByUserId(userId);
    }

    public boolean logMeOut(String accessToken) {
        String bearerToken = tokenExtractor.extract(accessToken);
        return tokenService.deleteByAccessToken(bearerToken);
    }

    public String getMyUserId() {
        AppUserContext appUserContext = (AppUserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appUserContext.getUserId();
    }
}
