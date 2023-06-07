package com.sarfaraz.opticart.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenType {
    BEARER("Bearer ");

    private final String prefix;

}
