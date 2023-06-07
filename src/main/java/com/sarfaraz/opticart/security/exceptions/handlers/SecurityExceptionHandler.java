package com.sarfaraz.opticart.security.exceptions.handlers;

import com.sarfaraz.opticart.commons.dto.ExceptionResponseDto;
import com.sarfaraz.opticart.commons.exceptions.handlers.GlobalExceptionHandler;
import com.sarfaraz.opticart.commons.helper.ExceptionHelper;
import com.sarfaraz.opticart.security.exceptions.types.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class SecurityExceptionHandler extends GlobalExceptionHandler {

    private final ExceptionHelper exceptionHelper;
    public SecurityExceptionHandler(ExceptionHelper exceptionHelper) {
        super(exceptionHelper);
        this.exceptionHelper = exceptionHelper;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponseDto> handleAccountAlreadyExistsException(
            Exception exception,
            HttpServletRequest request) {
        return ResponseEntity.badRequest().body(exceptionHelper.getErrorResponse(request, exception, HttpStatus.BAD_REQUEST));
    }

}
