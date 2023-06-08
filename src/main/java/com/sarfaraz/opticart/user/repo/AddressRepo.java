package com.sarfaraz.opticart.user.repo;

import com.sarfaraz.opticart.user.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AddressRepo extends JpaRepository<Address, String> {

    Set<Address> findByUserId(String userId);

    Optional<Address> findByUserIdAndId(String userId, String id);

    Optional<Address> findByUserIdAndIsDefault(String userId, boolean isDefault);

    boolean existsByIdAndUserId(String id, String userId);

}
