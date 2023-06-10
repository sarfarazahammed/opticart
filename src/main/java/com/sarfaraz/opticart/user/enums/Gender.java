package com.sarfaraz.opticart.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Gender {
    M("male"), F("female");

    private final String value;
}
