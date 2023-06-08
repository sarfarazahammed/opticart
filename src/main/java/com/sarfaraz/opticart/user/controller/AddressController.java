package com.sarfaraz.opticart.user.controller;

import com.sarfaraz.opticart.commons.dto.ResponseDto;
import com.sarfaraz.opticart.commons.dto.SuccessResponseDto;
import com.sarfaraz.opticart.security.auth.jwt.JwtFacade;
import com.sarfaraz.opticart.security.exceptions.types.UserNotFoundException;
import com.sarfaraz.opticart.user.dto.AddressDto;
import com.sarfaraz.opticart.user.service.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user/address")
@Slf4j
@PreAuthorize("isAuthenticated()")
public class AddressController {

    private final JwtFacade jwtFacade;
    private final UserDetailsService userDetailsService;

    public AddressController(JwtFacade jwtFacade, UserDetailsService userDetailsService) {
        this.jwtFacade = jwtFacade;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("")
    public ResponseDto getMyAddresses() {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("List of your addresses")
                .data(userDetailsService.listAllAddress(userId)).build();
    }

    @GetMapping("/default")
    public ResponseDto getDefaultAddress() {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Default address successfully fetched")
                .data(userDetailsService.getDefaultAddress(userId)).build();
    }

    @PutMapping("/default")
    public ResponseDto changeDefaultAddress(@RequestParam("addressId") String addressId) {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Default address successfully fetched")
                .data(userDetailsService.makeDefault(addressId, userId)).build();
    }

    @PostMapping("")
    public ResponseDto addAddress(@Valid @RequestBody AddressDto addressDto) throws UserNotFoundException {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Address added successfully")
                .data(userDetailsService.addAddress(addressDto, userId)).build();
    }

    @PostMapping("update")
    public ResponseDto updateAddress(@Valid @RequestBody AddressDto addressDto) {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Address updated successfully")
                .data(userDetailsService.updateAddress(addressDto, userId)).build();
    }

    @DeleteMapping("")
    public ResponseDto deleteAddress(@RequestParam("addressId") String addressId) {
        String userId = jwtFacade.getMyUserId();
        userDetailsService.deleteAddress(userId, userId);
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Address deleted successfully")
                .build();
    }

}
