package com.sarfaraz.opticart.user.service;

import com.sarfaraz.opticart.security.exceptions.types.UserAlreadyExistsException;
import com.sarfaraz.opticart.security.exceptions.types.UserNotFoundException;
import com.sarfaraz.opticart.user.dto.UserDetailsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDetailsService {

    List<UserDetailsDto> listAllUsers(Pageable pageable);

    UserDetailsDto getUser(String userId) throws UserNotFoundException;

    UserDetailsDto updateUser(UserDetailsDto userDetailsDto, String userId) throws UserNotFoundException, UserAlreadyExistsException;

    void deleteUser(String userId);

}
