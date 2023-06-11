package com.sarfaraz.opticart.product.repo;

import com.sarfaraz.opticart.product.entity.LensIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LensIndexRepo extends JpaRepository<LensIndex, String> {
    Optional<LensIndex> findByIndex(double index);
}
