package com.sarfaraz.opticart.user.service;

import com.sarfaraz.opticart.security.entity.User;
import com.sarfaraz.opticart.security.enums.UserRole;
import com.sarfaraz.opticart.security.exceptions.types.UserAlreadyExistsException;
import com.sarfaraz.opticart.security.exceptions.types.UserNotFoundException;
import com.sarfaraz.opticart.security.repo.UserRepo;
import com.sarfaraz.opticart.user.dto.UserDetailsDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserDetailsDto> listAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable).stream()
                .map(user -> new UserDetailsDto(
                        user.getFirstName(), user.getLastName(), user.getProfileImageUrl(), user.getEmail(),
                        user.getPhoneNumber(), user.getState(),
                        user.getRoles().stream().map(role -> UserRole.valueOf(role.getName())).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsDto getUser(String userId) throws UserNotFoundException {
        User user = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        return new UserDetailsDto(
                user.getFirstName(), user.getLastName(), user.getProfileImageUrl(), user.getEmail(),
                user.getPhoneNumber(), user.getState(), null);
    }

    @Override
    public UserDetailsDto updateUser(UserDetailsDto userDetailsDto, String userId) throws UserNotFoundException, UserAlreadyExistsException {
        User user = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        if (!userDetailsDto.getEmail().equals(user.getEmail()) && userRepo.existsByEmail(userDetailsDto.getEmail())) {
            throw new UserAlreadyExistsException("Email is already used");
        }
        if (!userDetailsDto.getPhoneNumber().equals(user.getPhoneNumber()) && userRepo.existsByPhoneNumber(userDetailsDto.getPhoneNumber())) {
            throw new UserAlreadyExistsException("Phone Number is already used");
        }
        user.setEmail(userDetailsDto.getEmail());
        user.setPhoneNumber(userDetailsDto.getPhoneNumber());
        user.setFirstName(userDetailsDto.getFirstName());
        user.setLastName(userDetailsDto.getLastName());
        user.setProfileImageUrl(userDetailsDto.getProfileImageUrl());
        userRepo.save(user);
        return userDetailsDto;
    }

    @Override
    public void deleteUser(String userId) {
        userRepo.deleteById(userId);
    }
}
