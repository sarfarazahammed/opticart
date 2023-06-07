package com.sarfaraz.opticart.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponseDto extends ResponseDto {

    private String stackTrace;
    private List<ValidationError> errors;

    public ExceptionResponseDto(int statusCode, String message, String stackTrace) {
        super(HttpStatus.resolve(statusCode), message);
        this.stackTrace = stackTrace;
    }

    public ExceptionResponseDto(HttpStatus status, String message) {
        super(status, message);
    }

    public void addValidationError(String field, String message) {
        if (Objects.isNull(errors)) {
            errors = new ArrayList<>();
        }
        errors.add(new ValidationError(field, message));
    }

    @Data
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }
}


