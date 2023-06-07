package com.sarfaraz.opticart.security.entity;

import com.sarfaraz.opticart.security.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_token", schema = "app")
public class Token {

    @Id
    private String id;

    @Column(unique = true)
    private String accessToken;
    @Column(unique = true)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private LocalDateTime accessExpirationTime;
    private LocalDateTime refreshExpirationTime;

    private String userId;
}
