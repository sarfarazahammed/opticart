package com.sarfaraz.opticart.security.repo;

import com.sarfaraz.opticart.security.entity.PasswordRecovery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PasswordRecoveryRepo extends JpaRepository<PasswordRecovery, String> {

    boolean existsByUserIdAndCodeAndExpiryTimeGreaterThanEqual(String userId, String code, LocalDateTime expiryTime);

    void deleteByUserId(String userId);

}
