package com.sarfaraz.opticart.user.repo;

import com.sarfaraz.opticart.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member, String> {

    List<Member> findByUserId(String userId);

    Optional<Member> findByIdAndUserId(String id, String userId);

    void deleteByIdAndUserId(String id, String userId);

}
