package com.sarfaraz.opticart.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecoveryValidateDto {
    @NotBlank
    private String username;
    private String userId;
    @NotBlank
    private String code;
}
