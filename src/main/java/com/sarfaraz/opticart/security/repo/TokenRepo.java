package com.sarfaraz.opticart.security.repo;

import com.sarfaraz.opticart.security.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<Token, Long> {


    Token findByRefreshToken(String token);

    Boolean existsByAccessToken(String accessToken);

    Boolean existsByRefreshToken(String refreshToken);

    void deleteByAccessToken(String accessToken);

    void deleteAllByUserId(String userId);


}
