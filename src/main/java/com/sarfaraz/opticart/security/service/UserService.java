package com.sarfaraz.opticart.security.service;

import com.google.common.collect.ImmutableSet;
import com.sarfaraz.opticart.security.commons.model.JwtUser;
import com.sarfaraz.opticart.security.dto.AuthRequestDto;
import com.sarfaraz.opticart.security.dto.SignupDto;
import com.sarfaraz.opticart.security.enums.SignupStatus;
import com.sarfaraz.opticart.security.enums.UserRole;
import com.sarfaraz.opticart.security.exceptions.types.UserAlreadyExistsException;

public interface UserService {
    SignupStatus validateEmail(String email);

    SignupStatus validatePhone(String phoneNumber);

    String addUser(SignupDto signupDto) throws UserAlreadyExistsException;

    void addUserWithRole(SignupDto signupDto, UserRole role) throws UserAlreadyExistsException;

    boolean assignRole(UserRole role, String userId);

    boolean removeRoleFromUser(UserRole role, String userId);

    boolean existsById(String userId);

    ImmutableSet<String> getAuthorities(String userId);

    JwtUser authenticate(AuthRequestDto authRequestDto);

    boolean sendPasswordRecoveryCode(String username);

    String validatePasswordResetCode(String username, String code);

    boolean resetPassword(String username, String newPassword);
}
