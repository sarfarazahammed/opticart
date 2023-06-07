package com.sarfaraz.opticart.security.commons.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class JwtUser {
    private final String userId;
    private final Set<String> unmodifiableScopes;
}
