package com.sarfaraz.opticart.security.service;

import com.sarfaraz.opticart.security.commons.token.AccessToken;
import com.sarfaraz.opticart.security.commons.token.RefreshToken;
import com.sarfaraz.opticart.security.dto.AuthTokenDto;
import com.sarfaraz.opticart.security.enums.TokenType;

public interface TokenService {
    boolean isValidAccessToken(String accessToken);

    boolean isValidRefreshToken(String refreshToken);

    void updateTokensByRefreshToken(AccessToken accessToken, RefreshToken refreshToken, String oldRefreshToken);

    AuthTokenDto saveTokens(AccessToken accessToken, RefreshToken refreshToken, TokenType tokenType, String userId);

    boolean deleteByUserId(String userId);

    boolean deleteByAccessToken(String accessToken);

}
