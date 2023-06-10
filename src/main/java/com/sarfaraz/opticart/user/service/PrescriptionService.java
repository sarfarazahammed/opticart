package com.sarfaraz.opticart.user.service;

import com.sarfaraz.opticart.security.exceptions.types.UserNotFoundException;
import com.sarfaraz.opticart.user.dto.MemberDto;
import com.sarfaraz.opticart.user.dto.PrescriptionDto;
import com.sarfaraz.opticart.user.dto.PrescriptionTypeDto;

import java.util.List;

public interface PrescriptionService {

    // Prescription Type
    List<PrescriptionTypeDto> getAllPrescriptionTypes();

    PrescriptionTypeDto getPrescriptionType(String prescriptionId);

    PrescriptionTypeDto addPrescriptionType(PrescriptionTypeDto prescriptionTypeDto);

    PrescriptionTypeDto updatePrescriptionType(PrescriptionTypeDto prescriptionTypeDto);


    // member
    List<MemberDto> getMembers(String userId) throws UserNotFoundException;

    MemberDto getMemberDetail(String memberId);

    MemberDto getMemberDetailByUser(String memberId, String userId);

    MemberDto addMember(MemberDto memberDto, String userId) throws UserNotFoundException;

    MemberDto updateMember(MemberDto memberDto, String userId);

    void removeMember(String memberId, String userId);


    // prescription
    PrescriptionDto addPrescription(PrescriptionDto prescriptionDto, String memberId, String userId);

    PrescriptionDto updatePrescription(PrescriptionDto prescriptionDto, String userId);

    void removePrescription(String prescriptionId, String userId);


}
