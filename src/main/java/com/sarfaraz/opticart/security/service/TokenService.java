package com.sarfaraz.opticart.security.service;

import com.sarfaraz.opticart.security.commons.token.AccessToken;
import com.sarfaraz.opticart.security.commons.token.RefreshToken;
import com.sarfaraz.opticart.security.dto.AuthTokenDto;
import com.sarfaraz.opticart.security.enums.TokenType;

public interface TokenService {
    public boolean isValidAccessToken(String accessToken);

    public boolean isValidRefreshToken(String refreshToken);

    public void updateTokensByRefreshToken(AccessToken accessToken, RefreshToken refreshToken, String oldRefreshToken);

    public AuthTokenDto saveTokens(AccessToken accessToken, RefreshToken refreshToken, TokenType tokenType, String userId);

    public boolean deleteByUserId(String userId);

    public boolean deleteByAccessToken(String accessToken);

}
