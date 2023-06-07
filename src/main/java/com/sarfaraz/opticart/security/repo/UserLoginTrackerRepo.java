package com.sarfaraz.opticart.security.repo;

import com.sarfaraz.opticart.security.entity.UserLoginTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginTrackerRepo extends JpaRepository<UserLoginTracker, String> {
}
