package com.sarfaraz.opticart.security.service;

import com.google.common.collect.ImmutableSet;
import com.sarfaraz.opticart.security.commons.model.JwtUser;
import com.sarfaraz.opticart.security.dto.AuthRequestDto;
import com.sarfaraz.opticart.security.dto.SignupDto;
import com.sarfaraz.opticart.security.entity.PasswordRecovery;
import com.sarfaraz.opticart.security.entity.Role;
import com.sarfaraz.opticart.security.entity.User;
import com.sarfaraz.opticart.security.enums.SignupStatus;
import com.sarfaraz.opticart.security.enums.UserRole;
import com.sarfaraz.opticart.security.enums.UserState;
import com.sarfaraz.opticart.security.exceptions.types.UserAlreadyExistsException;
import com.sarfaraz.opticart.security.helper.PasswordHelper;
import com.sarfaraz.opticart.security.repo.PasswordRecoveryRepo;
import com.sarfaraz.opticart.security.repo.RoleRepo;
import com.sarfaraz.opticart.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.sarfaraz.opticart.commons.validator.RegexValidator.validEmail;
import static com.sarfaraz.opticart.security.enums.SignInStatus.INVALID_PASSWORD;
import static com.sarfaraz.opticart.security.enums.SignInStatus.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordRecoveryRepo passwordRecoveryRepo;
    private final PasswordHelper passwordHelper;
    private final Integer recoveryOtpExpSeconds;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordRecoveryRepo passwordRecoveryRepo,
                           PasswordHelper passwordHelper,
                           @Value("${jwt.recoveryOtpExpSeconds:300}") Integer recoveryOtpExpSeconds
    ) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordRecoveryRepo = passwordRecoveryRepo;
        this.passwordHelper = passwordHelper;
        this.recoveryOtpExpSeconds = recoveryOtpExpSeconds;
    }

    @Override
    public SignupStatus validateEmail(String email) {
        if (!validEmail(email)) return SignupStatus.INVALID_EMAIL;
        else if (userRepo.existsByEmail(email)) return SignupStatus.EMAIL_ALREADY_USED;
        else return SignupStatus.VALID;
    }

    @Override
    public SignupStatus validatePhone(String phoneNumber) {
        if (phoneNumber.length() < 10 || phoneNumber.length() > 15) return SignupStatus.INVALID_PHONE_NUMBER;
        else if (userRepo.existsByPhoneNumber(phoneNumber)) return SignupStatus.PHONE_NUMBER_ALREADY_USED;
        else return SignupStatus.VALID;
    }

    @Override
    @Transactional
    public String addUser(SignupDto signupDto) throws UserAlreadyExistsException {
        validateUserBeforeSignup(signupDto);
        User user = prepareUser(signupDto);
        userRepo.save(user);
        return user.getId();
    }

    private void validateUserBeforeSignup(SignupDto signupDto) throws UserAlreadyExistsException {
        if (userRepo.existsByEmail(signupDto.getEmail()))
            throw new UserAlreadyExistsException("Email Already exists Exception");
        if (userRepo.existsByPhoneNumber(signupDto.getPhoneNumber()))
            throw new UserAlreadyExistsException("Phone number already exists Exception");
    }

    @Override
    public void addUserWithRole(SignupDto signupDto, UserRole role) throws UserAlreadyExistsException {
        validateUserBeforeSignup(signupDto);
        User user = prepareUser(signupDto);
        user.setRoles(Collections.singleton(roleRepo.findRoleByName(role.name())));
        userRepo.save(user);
    }

    private User prepareUser(SignupDto signupDto) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(signupDto.getEmail());
        user.setPhoneNumber(signupDto.getPhoneNumber());
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());
        user.setPassword(passwordHelper.generateHashedPassword(signupDto.getPassword()));
        user.setState(UserState.PENDING);
        return user;
    }

    @Override
    public boolean assignRole(UserRole role, String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.name()));
        user.assignRole(roleRepo.findRoleByName(role.name()));
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean removeRoleFromUser(UserRole role, String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.name()));
        if (user.getRoles().removeIf(role1 -> role1.getName().equals(role.name()))) {
            userRepo.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean existsById(String userId) {
        return userRepo.existsById(userId);
    }

    @Override
    public ImmutableSet<String> getAuthorities(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.name()));
        return user.getAuthorities();
    }

    @Override
    public JwtUser authenticate(AuthRequestDto authRequestDto) {
        User user = userRepo.findOneByEmailOrPhoneNumber(authRequestDto.getEmail(), authRequestDto.getPhoneNumber());
        if (user == null) {
            throw new IllegalArgumentException(USER_NOT_FOUND.name());

        } else if (!passwordHelper.validatePassword(authRequestDto.getPassword(), user.getPassword()))
            throw new IllegalArgumentException(INVALID_PASSWORD.name());
        else {
            return new JwtUser(user.getId(), user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean sendPasswordRecoveryCode(String username) {
        User user = Optional.ofNullable(userRepo.findOneByEmailOrPhoneNumber(username, username)).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.name()));
        passwordRecoveryRepo.save(
                PasswordRecovery.builder()
                        .id(UUID.randomUUID().toString())
                        .userId(user.getId())
                        .code(String.valueOf(ThreadLocalRandom.current().nextInt(100_000, 999_999 + 1)))
                        .expiryTime(LocalDateTime.ofInstant(Instant.now().plus(recoveryOtpExpSeconds, ChronoUnit.SECONDS), ZoneId.of("UTC")))
                        .build());
        //TODO: SEND mail or message
        return true;
    }

    @Override
    public String validatePasswordResetCode(String username, String code) {
        User user = Optional.ofNullable(userRepo.findOneByEmailOrPhoneNumber(username, username)).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.name()));
        boolean isValidCode = passwordRecoveryRepo.existsByUserIdAndCodeAndExpiryTimeGreaterThanEqual(user.getId(), code, LocalDateTime.now(ZoneId.of("UTC")));
        return isValidCode ? user.getId() : null;
    }

    @Override
    @Transactional
    public boolean resetPassword(String userId, String newPassword) {
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND.name()));
        user.setPassword(passwordHelper.generateHashedPassword(newPassword));
        userRepo.save(user);
        passwordRecoveryRepo.deleteByUserId(userId);
        return true;
    }
}
