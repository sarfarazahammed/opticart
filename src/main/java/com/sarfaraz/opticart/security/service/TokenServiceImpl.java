package com.sarfaraz.opticart.security.service;

import com.sarfaraz.opticart.security.commons.token.AccessToken;
import com.sarfaraz.opticart.security.commons.token.RefreshToken;
import com.sarfaraz.opticart.security.dto.AuthTokenDto;
import com.sarfaraz.opticart.security.entity.Token;
import com.sarfaraz.opticart.security.enums.TokenType;
import com.sarfaraz.opticart.security.repo.TokenRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepo tokenRepo;

    public TokenServiceImpl(TokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    @Override
    public boolean isValidRefreshToken(String refreshToken) {
        return tokenRepo.existsByRefreshToken(refreshToken);
    }

    @Override
    public boolean isValidAccessToken(String accessToken) {
        return tokenRepo.existsByAccessToken(accessToken);
    }

    @Override
    @Transactional
    public void updateTokensByRefreshToken(AccessToken accessToken, RefreshToken refreshToken, String oldRefreshToken) {
        Token token = tokenRepo.findByRefreshToken(oldRefreshToken);
        if (token == null) throw new IllegalStateException("Invalid Refresh Token");
        token.setAccessToken(accessToken.getToken());
        token.setRefreshToken(refreshToken.getToken());
        token.setAccessExpirationTime(accessToken.getExpirationTime());
        token.setRefreshExpirationTime(refreshToken.getExpirationTime());
        tokenRepo.save(token);
    }

    @Override
    @Transactional
    public AuthTokenDto saveTokens(AccessToken accessToken, RefreshToken refreshToken, TokenType tokenType, String userId) {
        tokenRepo.save(Token.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .accessToken(accessToken.getToken())
                .refreshToken(refreshToken.getToken())
                .accessExpirationTime(accessToken.getExpirationTime())
                .refreshExpirationTime(refreshToken.getExpirationTime())
                .tokenType(tokenType)
                .build());
        return AuthTokenDto.builder()
                .accessToken(accessToken.getToken())
                .refreshToken(refreshToken.getToken())
                .tokenType(tokenType)
                .build();
    }

    @Override
    @Transactional
    public boolean deleteByUserId(String userId) {
        tokenRepo.deleteAllByUserId(userId);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteByAccessToken(String accessToken) {
        tokenRepo.deleteByAccessToken(accessToken);
        return true;
    }
}
