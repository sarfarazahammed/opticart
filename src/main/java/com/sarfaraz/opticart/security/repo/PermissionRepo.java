package com.sarfaraz.opticart.security.repo;

import com.sarfaraz.opticart.security.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepo extends JpaRepository<Permission, String> {
}
