package com.sarfaraz.opticart.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResponseDto {

    private HttpStatus code;
    private String message;

}
