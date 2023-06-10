package com.sarfaraz.opticart.user.service;

import com.sarfaraz.opticart.security.entity.User;
import com.sarfaraz.opticart.security.exceptions.types.UserNotFoundException;
import com.sarfaraz.opticart.security.repo.UserRepo;
import com.sarfaraz.opticart.user.converter.MemberConverter;
import com.sarfaraz.opticart.user.converter.PrescriptionConverter;
import com.sarfaraz.opticart.user.converter.PrescriptionTypeConverter;
import com.sarfaraz.opticart.user.dto.MemberDto;
import com.sarfaraz.opticart.user.dto.PrescriptionDto;
import com.sarfaraz.opticart.user.dto.PrescriptionTypeDto;
import com.sarfaraz.opticart.user.entity.Member;
import com.sarfaraz.opticart.user.entity.Prescription;
import com.sarfaraz.opticart.user.entity.PrescriptionType;
import com.sarfaraz.opticart.user.repo.MemberRepo;
import com.sarfaraz.opticart.user.repo.PrescriptionRepo;
import com.sarfaraz.opticart.user.repo.PrescriptionTypeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {


    private final MemberConverter memberConverter;
    private final PrescriptionConverter prescriptionConverter;
    private final PrescriptionRepo prescriptionRepo;
    private final PrescriptionTypeRepo prescriptionTypeRepo;
    private final PrescriptionTypeConverter prescriptionTypeConverter;
    private final UserRepo userRepo;
    private final MemberRepo memberRepo;

    public PrescriptionServiceImpl(MemberRepo memberRepo, MemberConverter memberConverter, PrescriptionConverter prescriptionConverter, PrescriptionRepo prescriptionRepo, PrescriptionTypeRepo prescriptionTypeRepo, PrescriptionTypeConverter prescriptionTypeConverter, UserRepo userRepo) {
        this.prescriptionConverter = prescriptionConverter;
        this.prescriptionTypeRepo = prescriptionTypeRepo;
        this.prescriptionTypeConverter = prescriptionTypeConverter;
        this.userRepo = userRepo;
        this.memberRepo = memberRepo;
        this.memberConverter = memberConverter;
        this.prescriptionRepo = prescriptionRepo;
    }

    @Override
    public MemberDto getMemberDetail(String memberId) {
        Member member = memberRepo.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Prescriptions not found"));
        return getMemberDto(member);
    }

    private MemberDto getMemberDto(Member member) {
        MemberDto memberDto = memberConverter.convertToDto(member);
        memberDto.setPrescriptions(
                member.getPrescriptions().stream().map(prescriptionConverter::convertToDto).collect(Collectors.toSet()));
        return memberDto;
    }

    @Override
    public MemberDto getMemberDetailByUser(String memberId, String userId) {
        Member member = memberRepo.findByIdAndUserId(memberId, userId).orElseThrow(() -> new IllegalArgumentException("Prescriptions not found"));
        return getMemberDto(member);
    }

    @Override
    public List<MemberDto> getMembers(String userId) {
        return memberRepo.findByUserId(userId).stream()
                .filter(Objects::nonNull)
                .map(this::getMemberDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MemberDto addMember(MemberDto memberDto, String userId) throws UserNotFoundException {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Member member = memberConverter.convertToEntity(memberDto);
        member.setPrescriptions(
                memberDto.getPrescriptions().stream().
                        filter(Objects::nonNull).
                        map(prescriptionDto -> {
                            Prescription prescription = prescriptionConverter.convertToEntity(prescriptionDto);
                            prescription.setMember(member);
                            return prescription;
                        }).
                        collect(Collectors.toSet())
        );
        member.setUser(user);
        MemberDto newMemberDto = memberConverter.convertToDto(memberRepo.save(member));
        newMemberDto.setPrescriptions(member.getPrescriptions().stream().map(prescriptionConverter::convertToDto).collect(Collectors.toSet()));
        return newMemberDto;
    }

    @Override
    public PrescriptionDto addPrescription(PrescriptionDto prescriptionDto,
                                           String memberId, String userId) {
        Member member = memberRepo.findByIdAndUserId(memberId, userId).orElseThrow(() -> new IllegalArgumentException("Prescriptions not found"));
        Prescription prescription = prescriptionConverter.convertToEntity(prescriptionDto);
        member.getPrescriptions().add(prescription);
        prescription.setMember(member);
        memberRepo.save(member);
        return prescriptionConverter.convertToDto(prescription);
    }

    @Override
    @Transactional
    public MemberDto updateMember(MemberDto memberDto, String userId) {
        Member member = memberRepo.findByIdAndUserId(memberDto.getId(), userId).orElseThrow(() -> new IllegalArgumentException("Prescriptions not found"));
        member = memberConverter.convertToEntity(memberDto, member);
        memberRepo.save(member);
        MemberDto newMemberDto = memberConverter.convertToDto(memberRepo.save(member));
        newMemberDto.setPrescriptions(member.getPrescriptions().stream().map(prescriptionConverter::convertToDto).collect(Collectors.toSet()));
        return newMemberDto;
    }

    @Override
    @Transactional
    public PrescriptionDto updatePrescription(PrescriptionDto prescriptionDto, String userId) {
        Prescription prescription = prescriptionRepo.findByIdAndMemberUserId(prescriptionDto.getId(), userId).orElseThrow(() -> new IllegalArgumentException("Prescriptions not found"));
        prescription = prescriptionConverter.convertToEntity(prescriptionDto, prescription);

        Member member = prescription.getMember();
        member.getPrescriptions().removeIf(oldPrescription -> oldPrescription.getId().equals(prescriptionDto.getId()));
        member.getPrescriptions().add(prescription);

        memberRepo.save(member);


        return prescriptionConverter.convertToDto(prescription);
    }

    @Override
    @Transactional
    public void removePrescription(String prescriptionId, String userId) {
        prescriptionRepo.deleteByIdAndMemberUserId(prescriptionId, userId);
    }

    @Override
    @Transactional
    public void removeMember(String memberId, String userId) {
        memberRepo.deleteByIdAndUserId(memberId, userId);
    }

    @Override
    public List<PrescriptionTypeDto> getAllPrescriptionTypes() {
        return prescriptionTypeRepo.findAll().stream().map(prescriptionTypeConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public PrescriptionTypeDto getPrescriptionType(String prescriptionId) {
        return prescriptionTypeConverter.convertToDto(prescriptionTypeRepo.findById(prescriptionId).orElseThrow(() -> new IllegalArgumentException("Prescription type not found")));
    }

    @Override
    @Transactional
    public PrescriptionTypeDto addPrescriptionType(PrescriptionTypeDto prescriptionTypeDto) {
        if (prescriptionTypeDto == null) return null;
        PrescriptionType prescriptionType = prescriptionTypeConverter.convertToEntity(prescriptionTypeDto);
        prescriptionType = prescriptionTypeRepo.save(prescriptionType);
        return prescriptionTypeConverter.convertToDto(prescriptionType);
    }

    @Override
    @Transactional
    public PrescriptionTypeDto updatePrescriptionType(PrescriptionTypeDto prescriptionTypeDto) {
        PrescriptionType prescriptionType = prescriptionTypeRepo.findById(prescriptionTypeDto.getId()).orElseThrow(() -> new IllegalArgumentException("Prescription type not found"));
        prescriptionTypeConverter.convertToEntity(prescriptionTypeDto, prescriptionType);
        prescriptionType = prescriptionTypeRepo.save(prescriptionType);
        return prescriptionTypeConverter.convertToDto(prescriptionType);
    }
}
