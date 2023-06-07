package com.sarfaraz.opticart.user.dto;

import com.sarfaraz.opticart.security.enums.UserRole;
import lombok.Data;

@Data
public class UserRoleChangeDto {
    private UserRole role;
    private String userId;
}
