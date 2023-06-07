package com.sarfaraz.opticart.security.helper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHelper {

    private final PasswordEncoder passwordEncoder;

    public PasswordHelper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validatePassword(String password, String candidatePassword) {
        boolean result = false;
        if (password != null) {
            result = passwordEncoder.matches(password, candidatePassword);
        }
        return result;
    }

    public String generateHashedPassword(String plainText) {
        return passwordEncoder.encode(plainText);
    }
}
