package com.sarfaraz.opticart.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarfaraz.opticart.security.enums.UserRole;
import com.sarfaraz.opticart.security.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsDto {

    private String firstName;
    private String lastName;
    private String profileImageUrl;
    private String email;
    private String phoneNumber;
    private UserState state;
    private List<UserRole> roles;

}
