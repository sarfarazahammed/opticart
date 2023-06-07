package com.sarfaraz.opticart.security.repo;

import com.sarfaraz.opticart.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
    User findOneByEmailOrPhoneNumber(String email, String phoneNumber);

    User findOneByEmail(String email);

    User findOneByPhoneNumber(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
}
