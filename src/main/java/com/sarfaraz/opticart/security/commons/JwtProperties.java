package com.sarfaraz.opticart.security.commons;

import com.sarfaraz.opticart.commons.helper.RsaKeyHelper;
import com.sarfaraz.opticart.security.enums.TokenType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static com.sarfaraz.opticart.security.commons.constants.JwtConstants.JWT_PROPERTIES_PREFIX;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConfigurationProperties(prefix = JWT_PROPERTIES_PREFIX)
@Data
public class JwtProperties {
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration tokenExpirationSeconds;
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration refreshTokenExpSeconds;
    private String tokenIssuer;
    private String privateKeyStr;
    private TokenType activeTokenType;

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    @PostConstruct
    public void init() throws Exception {
        privateKey = RsaKeyHelper.getPrivateKeyFromString(this.privateKeyStr);
        publicKey = RsaKeyHelper.getPublicKey(privateKey);
    }
}
