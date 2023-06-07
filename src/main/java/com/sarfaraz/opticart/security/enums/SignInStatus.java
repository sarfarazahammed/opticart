package com.sarfaraz.opticart.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum SignInStatus {
    USER_NOT_FOUND(BAD_REQUEST), SUCCESS(OK), INVALID_PASSWORD(BAD_REQUEST), SYSTEM_ERROR(INTERNAL_SERVER_ERROR), TAMPERED_REQUEST(BAD_REQUEST);

    private final HttpStatus httpStatus;
}
