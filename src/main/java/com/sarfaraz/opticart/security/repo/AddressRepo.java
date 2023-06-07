package com.sarfaraz.opticart.security.repo;

import com.sarfaraz.opticart.security.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
