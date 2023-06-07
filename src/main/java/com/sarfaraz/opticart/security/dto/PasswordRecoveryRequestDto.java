package com.sarfaraz.opticart.security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordRecoveryRequestDto {
    @NotBlank
    private String username;
}
