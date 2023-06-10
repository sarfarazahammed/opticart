package com.sarfaraz.opticart.user.repo;

import com.sarfaraz.opticart.user.entity.PrescriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionTypeRepo extends JpaRepository<PrescriptionType, String> {
}
