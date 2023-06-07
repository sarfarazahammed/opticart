package com.sarfaraz.opticart.security.auth.controller;

import com.sarfaraz.opticart.commons.dto.ResponseDto;
import com.sarfaraz.opticart.commons.dto.SuccessResponseDto;
import com.sarfaraz.opticart.security.auth.jwt.JwtFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.sarfaraz.opticart.security.commons.constants.JwtConstants.JWT_TOKEN_HEADER_PARAM;

@RestController
@RequestMapping("/api/v1/logout")
@Slf4j
public class LogoutController {

    private final JwtFacade jwtFacade;

    public LogoutController(JwtFacade jwtFacade) {
        this.jwtFacade = jwtFacade;
    }

    @GetMapping(value = "all")
    @ResponseBody
    public ResponseDto logoutAll() {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Logged out from all successfully")
                .data(jwtFacade.logMeOutFromAllDevices())
                .build();
    }

    @GetMapping(value = "/")
    @ResponseBody
    public ResponseDto logout(@RequestHeader(JWT_TOKEN_HEADER_PARAM) String accessToken) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Logged out successfully")
                .data(jwtFacade.logMeOut(accessToken))
                .build();
    }
}
