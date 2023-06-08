package com.sarfaraz.opticart.user.service;

import com.sarfaraz.opticart.security.entity.User;
import com.sarfaraz.opticart.security.enums.UserRole;
import com.sarfaraz.opticart.security.exceptions.types.UserAlreadyExistsException;
import com.sarfaraz.opticart.security.exceptions.types.UserNotFoundException;
import com.sarfaraz.opticart.security.repo.UserRepo;
import com.sarfaraz.opticart.user.converter.AddressConverter;
import com.sarfaraz.opticart.user.dto.AddressDto;
import com.sarfaraz.opticart.user.dto.UserDetailsDto;
import com.sarfaraz.opticart.user.entity.Address;
import com.sarfaraz.opticart.user.repo.AddressRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    private final AddressRepo addressRepo;
    private final AddressConverter addressConverter;

    public UserDetailsServiceImpl(UserRepo userRepo, AddressRepo addressRepo, AddressConverter addressConverter) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.addressConverter = addressConverter;
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

    @Override
    public List<AddressDto> listAllAddress(String userId) {
        return addressRepo.findByUserId(userId).stream()
                .map(addressConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AddressDto addAddress(AddressDto addressDto, String userId) throws UserNotFoundException {
        User user = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        if (addressDto.isDefault()) {
            Address address = addressRepo.findByUserIdAndIsDefault(userId, true).orElse(null);
            if (address != null) {
                address.setDefault(false);
                addressRepo.save(address);
            }
        }
        Address address = addressConverter.convertToEntity(addressDto);
        String id = UUID.randomUUID().toString();
        address.setId(id);
        address.setUser(user);
        user.addAddress(address);
        userRepo.save(user);
        return addressConverter.convertToDto(address);
    }

    @Override
    @Transactional
    public AddressDto updateAddress(AddressDto addressDto, String userId) {

        if (addressDto.isDefault()) {
            Address address = addressRepo.findByUserIdAndIsDefault(userId, true).orElse(null);
            if (address != null && !address.getId().equals(addressDto.getId())) {
                address.setDefault(false);
                addressRepo.save(address);
            }
        }
        String addressId = addressDto.getId();
        Address address = addressRepo.findByUserIdAndId(userId, addressId).orElseThrow(() -> new IllegalArgumentException("Address not found for id : " + addressId));
        Address newAddress = addressConverter.convertToEntity(addressDto, address);
        addressRepo.save(newAddress);
        return addressConverter.convertToDto(newAddress);
    }

    @Override
    @Transactional
    public AddressDto makeDefault(String addressId, String userId) {
        Address newDefaultAddress = addressRepo.findByUserIdAndId(userId, addressId).orElseThrow(() -> new IllegalArgumentException("Address not found for id : " + addressId));

        Address address = addressRepo.findByUserIdAndIsDefault(userId, true).orElse(null);
        if (address != null && !address.getId().equals(addressId)) {
            address.setDefault(false);
            addressRepo.save(address);
        }
        newDefaultAddress.setDefault(true);
        return addressConverter.convertToDto(addressRepo.save(newDefaultAddress));
    }

    @Override
    public AddressDto getDefaultAddress(String userId) {
        Address address = addressRepo.findByUserIdAndIsDefault(userId, true).orElse(null);
        return addressConverter.convertToDto(address);
    }

    @Override
    @Transactional
    public void deleteAddress(String addressId, String userId) {
        if (addressRepo.existsByIdAndUserId(addressId, userId))
            addressRepo.deleteById(addressId);
        else
            throw new IllegalArgumentException("Address is not found for the specified user");

    }
}
