package com.sarfaraz.opticart.commons.exceptions.handlers;

import com.sarfaraz.opticart.commons.dto.ExceptionResponseDto;
import com.sarfaraz.opticart.commons.helper.ExceptionHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ExceptionHelper exceptionHelper;

    public GlobalExceptionHandler(ExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }

    @ExceptionHandler({ValidationException.class, IllegalArgumentException.class, LoginException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponseDto> handleValidationErrors(Exception exception, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(exceptionHelper.getErrorResponse(request, exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponseDto> handleAllUncaughtException(Exception exception, HttpServletRequest request) {
        log.error("Unknown error occurred", exception);
        return ResponseEntity.internalServerError().body(exceptionHelper.getErrorResponse(request, exception, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
