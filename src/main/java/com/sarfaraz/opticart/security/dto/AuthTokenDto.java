package com.sarfaraz.opticart.security.dto;

import com.sarfaraz.opticart.security.enums.TokenType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenDto {
    private String accessToken;
    private String refreshToken;
    private TokenType tokenType;
}
