package com.sarfaraz.opticart.security.auth.controller;

import com.sarfaraz.opticart.commons.dto.ResponseDto;
import com.sarfaraz.opticart.commons.dto.SuccessResponseDto;
import com.sarfaraz.opticart.security.auth.jwt.JwtFacade;
import com.sarfaraz.opticart.security.dto.*;
import com.sarfaraz.opticart.security.enums.SignupStatus;
import com.sarfaraz.opticart.security.enums.UserRole;
import com.sarfaraz.opticart.security.exceptions.types.UserAlreadyExistsException;
import com.sarfaraz.opticart.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

import static com.sarfaraz.opticart.security.commons.constants.JwtConstants.JWT_REFRESH_TOKEN_HEADER_PARAM;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtFacade jwtFacade;

    public AuthController(UserService userService, JwtFacade jwtFacade) {
        this.userService = userService;
        this.jwtFacade = jwtFacade;
    }

    @GetMapping(value = "signup/validate")
    public ResponseDto signupValidation(@RequestParam(name = "email", required = false) String email, @RequestParam(name = "phone", required = false) String phone) {
        HashMap<String, SignupStatus> response = new HashMap<>();
        if (StringUtils.isNotBlank(email)) {
            response.put("emailStatus", jwtFacade.validateEmail(email));
        }
        if (StringUtils.isNotBlank(phone)) {
            response.put("phoneStatus", jwtFacade.validatePhone(phone));
        }
        return SuccessResponseDto.builder().code(HttpStatus.OK).message("Validation Successfull").data(response).build();

    }

    @PostMapping(value = "signup")
    @ResponseBody
    public ResponseDto signup(@Valid @RequestBody SignupDto signupDto) throws UserAlreadyExistsException {
        userService.addUserWithRole(signupDto, UserRole.ROLE_CUSTOMER);
        ResponseDto response = tokens(new AuthRequestDto(signupDto.getEmail(), signupDto.getPhoneNumber(), signupDto.getPassword()));
        //TODO: SEND mail for new user
        response.setMessage("Signed Up successfully");
        return response;
    }

    @PostMapping(value = "tokens")
    @ResponseBody
    public ResponseDto tokens(@RequestBody AuthRequestDto requestDto) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Tokens created")
                .data(jwtFacade.getTokens(userService.authenticate(requestDto)))
                .build();
    }

    @GetMapping(value = "tokens/refresh")
    @ResponseBody
    public ResponseDto refreshToken(@RequestHeader(JWT_REFRESH_TOKEN_HEADER_PARAM) String refreshToken) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Tokens refreshed").data(jwtFacade.refreshToken(refreshToken))
                .build();
    }

    @PostMapping(value = "password/recovery")
    @ResponseBody
    public ResponseDto recoverPassword(@Valid @RequestBody PasswordRecoveryRequestDto requestDto) {
        if (StringUtils.isNotBlank(requestDto.getUsername()) && userService.sendPasswordRecoveryCode(requestDto.getUsername()))
            return SuccessResponseDto.builder()
                    .code(HttpStatus.OK)
                    .message("Password recovery code sent")
                    .build();
        else
            throw new IllegalArgumentException("Could not send recovery token");
    }

    @PostMapping(value = "password/recovery/validate")
    @ResponseBody
    public ResponseDto validateRecoveryData(@Validated @RequestBody PasswordRecoveryValidateDto passwordRecoveryValidateDto) {
        String userId = userService.validatePasswordResetCode(passwordRecoveryValidateDto.getUsername(), passwordRecoveryValidateDto.getCode());
        if (StringUtils.isNotBlank(userId)) {
            return SuccessResponseDto.builder()
                    .code(HttpStatus.OK)
                    .message("Password recovery code validated successfully")
                    .data(new PasswordRecoveryValidateDto(passwordRecoveryValidateDto.getUsername(), userId, passwordRecoveryValidateDto.getCode()))
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid OTP received");
        }
    }

    @PostMapping(value = "password/change")
    @ResponseBody
    public ResponseDto changePassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto) {
        if (!passwordChangeDto.getPassword().equals(passwordChangeDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords did not match");
        }

        if (userService.resetPassword(passwordChangeDto.getUserId(), passwordChangeDto.getPassword())) {
            jwtFacade.logMeOutFromAllDevices(passwordChangeDto.getUserId());
            return SuccessResponseDto.builder()
                    .code(HttpStatus.OK)
                    .message("Password updated successfully! Try logging in")
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid OTP received");
        }
    }

}