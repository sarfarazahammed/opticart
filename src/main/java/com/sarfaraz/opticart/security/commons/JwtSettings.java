package com.sarfaraz.opticart.security.commons;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.interfaces.RSAPrivateKey;
import java.time.Duration;

@Data
@AllArgsConstructor
public class JwtSettings {
    private final Duration tokenExpirationTime;
    private final Duration refreshTokenExpTime;
    private final Duration inAppTokenExpTime;
    private final String tokenIssuer;
    private final RSAPrivateKey privateKey;
}
