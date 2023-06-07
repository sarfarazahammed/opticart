package com.sarfaraz.opticart.security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordChangeDto {
    @NotBlank
    private String userId;
    private String code;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}
