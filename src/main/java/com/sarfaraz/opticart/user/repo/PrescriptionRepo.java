package com.sarfaraz.opticart.user.repo;

import com.sarfaraz.opticart.user.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PrescriptionRepo extends JpaRepository<Prescription, String> {

    void deleteByIdAndMemberUserId(String prescriptionId, String userId);


    @Query("select p from Prescription p join fetch p.member join fetch p.power where p.id = :id and p.member.user.id = :userId")
    Optional<Prescription> findByIdAndMemberUserId(@Param("id") String prescriptionId, @Param("userId") String userId);
}
