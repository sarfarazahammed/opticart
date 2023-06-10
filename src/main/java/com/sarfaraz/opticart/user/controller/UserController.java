package com.sarfaraz.opticart.user.controller;


import com.sarfaraz.opticart.commons.dto.ResponseDto;
import com.sarfaraz.opticart.commons.dto.SuccessResponseDto;
import com.sarfaraz.opticart.security.auth.jwt.JwtFacade;
import com.sarfaraz.opticart.security.exceptions.types.UserAlreadyExistsException;
import com.sarfaraz.opticart.security.exceptions.types.UserNotFoundException;
import com.sarfaraz.opticart.security.service.UserService;
import com.sarfaraz.opticart.user.dto.UserDetailsDto;
import com.sarfaraz.opticart.user.dto.UserRoleChangeDto;
import com.sarfaraz.opticart.user.service.UserDetailsService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtFacade jwtFacade;

    public UserController(UserDetailsService userDetailsService, UserService userService, JwtFacade jwtFacade) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtFacade = jwtFacade;
    }

    @GetMapping("list/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseDto listAllUsers(Pageable pageable) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Listed All Users")
                .data(userDetailsService.listAllUsers(pageable)).build();
    }

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseDto currentUserDetails() throws UserNotFoundException {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("User Details")
                .data(userDetailsService.getUser(userId)).build();
    }

    @PutMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseDto updateUser(@RequestBody UserDetailsDto userDetailsDto) throws UserNotFoundException, UserAlreadyExistsException {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Updated User")
                .data(userDetailsService.updateUser(userDetailsDto, userId)).build();
    }

    @PostMapping("add/role")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseDto addRole(@RequestBody UserRoleChangeDto roleChangeDto) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Added role to user")
                .data(userService.assignRole(roleChangeDto.getRole(), roleChangeDto.getUserId())).build();
    }

    @PostMapping("remove/role")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseDto removeRole(@RequestBody UserRoleChangeDto roleChangeDto) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Removed Role from User")
                .data(userService.removeRoleFromUser(roleChangeDto.getRole(), roleChangeDto.getUserId())).build();
    }

    @DeleteMapping("delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseDto removeUser() {
        String userId = jwtFacade.getMyUserId();
        userDetailsService.deleteUser(userId);
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Deleted user Successfully")
                .build();
    }

}
