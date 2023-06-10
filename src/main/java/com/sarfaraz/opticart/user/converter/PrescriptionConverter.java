package com.sarfaraz.opticart.user.converter;

import com.sarfaraz.opticart.user.dto.PrescriptionDto;
import com.sarfaraz.opticart.user.entity.Prescription;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PrescriptionConverter implements Converter<Prescription, PrescriptionDto> {

    private final PowerConverter powerConverter;

    public PrescriptionConverter(PowerConverter powerConverter) {
        this.powerConverter = powerConverter;
    }

    @Override
    public Prescription convertToEntity(PrescriptionDto dto) {
        if (dto == null) return null;
        return Prescription.builder().
                id(UUID.randomUUID().toString()).
                typeId(dto.getPrescriptionTypeId()).
                power(powerConverter.convertToEntity(dto.getPower())).
                build();
    }

    @Override
    public PrescriptionDto convertToDto(Prescription entity) {
        if (entity == null) return null;
        return PrescriptionDto.builder()
                .id(entity.getId())
                .prescriptionTypeId(entity.getTypeId())
                .power(powerConverter.convertToDto(entity.getPower()))
                .createdDate(entity.getCreatedDate())
                .build();

    }

    public Prescription convertToEntity(PrescriptionDto prescriptionDto, Prescription prescription) {
        if (prescriptionDto == null) return null;
        prescription.setTypeId(prescriptionDto.getPrescriptionTypeId());
        prescription.setPower(powerConverter.convertToEntity(prescriptionDto.getPower(), prescription.getPower()));
        return prescription;
    }

}