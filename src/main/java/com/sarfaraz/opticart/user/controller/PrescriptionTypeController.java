package com.sarfaraz.opticart.user.controller;

import com.sarfaraz.opticart.commons.dto.ResponseDto;
import com.sarfaraz.opticart.commons.dto.SuccessResponseDto;
import com.sarfaraz.opticart.user.dto.PrescriptionTypeDto;
import com.sarfaraz.opticart.user.service.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/prescription/type")
public class PrescriptionTypeController {

    private final PrescriptionService prescriptionService;

    public PrescriptionTypeController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("all")
    public ResponseDto listAllTypes() {

        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription Types fetched Successfully")
                .data(prescriptionService.getAllPrescriptionTypes()).build();
    }

    @GetMapping("")
    public ResponseDto getType(@RequestParam("id") String id) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription Type fetched Successfully")
                .data(prescriptionService.getPrescriptionType(id)).build();
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseDto addype(@RequestBody PrescriptionTypeDto prescriptionTypeDto) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription Type added Successfully")
                .data(prescriptionService.addPrescriptionType(prescriptionTypeDto)).build();
    }

    @PutMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseDto updateType(@RequestBody PrescriptionTypeDto prescriptionTypeDto) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Prescription Type updated Successfully")
                .data(prescriptionService.updatePrescriptionType(prescriptionTypeDto)).build();
    }
}
