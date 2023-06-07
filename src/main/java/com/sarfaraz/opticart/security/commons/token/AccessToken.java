package com.sarfaraz.opticart.security.commons.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken implements AuthToken {
    private static final long serialVersionUID = 2405172041950251808L;

    private String token;
    private LocalDateTime expirationTime;
}
