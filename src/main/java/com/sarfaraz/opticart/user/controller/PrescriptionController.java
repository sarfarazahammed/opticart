package com.sarfaraz.opticart.user.controller;


import com.sarfaraz.opticart.commons.dto.ResponseDto;
import com.sarfaraz.opticart.commons.dto.SuccessResponseDto;
import com.sarfaraz.opticart.security.auth.jwt.JwtFacade;
import com.sarfaraz.opticart.security.exceptions.types.UserNotFoundException;
import com.sarfaraz.opticart.user.dto.MemberDto;
import com.sarfaraz.opticart.user.dto.PrescriptionDto;
import com.sarfaraz.opticart.user.dto.PrescriptionTypeDto;
import com.sarfaraz.opticart.user.service.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/prescription")
@PreAuthorize("isAuthenticated()")
public class PrescriptionController {

    private final JwtFacade jwtFacade;
    private final PrescriptionService prescriptionService;

    public PrescriptionController(JwtFacade jwtFacade, PrescriptionService prescriptionService) {
        this.jwtFacade = jwtFacade;
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("type/all")
    public ResponseDto listAllTypes() {

        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription Types fetched Successfully")
                .data(prescriptionService.getAllPrescriptionTypes()).build();
    }

    @GetMapping("type")
    public ResponseDto getType(@RequestParam("id") String id) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription Type fetched Successfully")
                .data(prescriptionService.getPrescriptionType(id)).build();
    }

    @PostMapping("type")
    public ResponseDto addype(@RequestBody PrescriptionTypeDto prescriptionTypeDto) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription Type added Successfully")
                .data(prescriptionService.addPrescriptionType(prescriptionTypeDto)).build();
    }

    @PutMapping("type")
    public ResponseDto updateType(@RequestBody PrescriptionTypeDto prescriptionTypeDto) {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription Type updated Successfully")
                .data(prescriptionService.updatePrescriptionType(prescriptionTypeDto)).build();
    }

    @GetMapping("list/all")
    public ResponseDto listAllMembers() throws UserNotFoundException {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescriptions fetched Successfully")
                .data(prescriptionService.getMembers(userId)).build();
    }

    @GetMapping("")
    public ResponseDto getPrescriptionDetails(@RequestParam("id") String memberId) {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription details fetched Successfully")
                .data(prescriptionService.getMemberDetailByUser(memberId, userId)).build();
    }

    @PostMapping("")
    public ResponseDto addPrescription(@RequestBody PrescriptionDto prescriptionDto, @RequestParam("memberId") String memberId) throws UserNotFoundException {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription added Successfully")
                .data(prescriptionService.addPrescription(prescriptionDto, memberId, userId)).build();
    }

    @PutMapping("")
    public ResponseDto updatePrescription(@RequestBody PrescriptionDto prescriptionDto) {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription updated Successfully")
                .data(prescriptionService.updatePrescription(prescriptionDto, userId)).build();
    }

    @DeleteMapping("")
    public ResponseDto removePrescription(@RequestParam("id") String id) {
        String userId = jwtFacade.getMyUserId();
        prescriptionService.removePrescription(id, userId);
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription deleted Successfully")
                .build();
    }

    @GetMapping("member")
    public ResponseDto getMember(@RequestParam("id") String id) {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Member added Successfully")
                .data(prescriptionService.getMemberDetail(id)).build();
    }

    @PostMapping("member")
    public ResponseDto addMember(@RequestBody MemberDto memberDto) throws UserNotFoundException {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Member added Successfully")
                .data(prescriptionService.addMember(memberDto, userId)).build();
    }

    @PutMapping("member")
    public ResponseDto updateMember(@RequestBody MemberDto memberDto) {
        String userId = jwtFacade.getMyUserId();
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Member updated Successfully")
                .data(prescriptionService.updateMember(memberDto, userId)).build();
    }

    @DeleteMapping("member")
    public ResponseDto removeMember(@RequestParam("id") String id) {
        String userId = jwtFacade.getMyUserId();
        prescriptionService.removeMember(id, userId);
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Member deleted Successfully")
                .build();
    }
}