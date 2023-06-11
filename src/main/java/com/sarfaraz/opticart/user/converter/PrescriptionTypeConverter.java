package com.sarfaraz.opticart.user.converter;

import com.sarfaraz.opticart.commons.converter.Converter;
import com.sarfaraz.opticart.user.dto.PrescriptionTypeDto;
import com.sarfaraz.opticart.user.entity.PrescriptionType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PrescriptionTypeConverter implements Converter<PrescriptionType, PrescriptionTypeDto> {
    @Override
    public PrescriptionType convertToEntity(PrescriptionTypeDto dto) {
        PrescriptionType prescriptionType = new PrescriptionType();
        prescriptionType.setId(UUID.randomUUID().toString());
        prescriptionType.setName(dto.getName());
        prescriptionType.setTitle(dto.getTitle());
        prescriptionType.setImgUrl(dto.getImgUrl());
        prescriptionType.setDescription(dto.getDescription());
        return prescriptionType;
    }

    @Override
    public PrescriptionTypeDto convertToDto(PrescriptionType entity) {
        PrescriptionTypeDto prescriptionTypeDto = new PrescriptionTypeDto();
        prescriptionTypeDto.setId(entity.getId());
        prescriptionTypeDto.setName(entity.getName());
        prescriptionTypeDto.setTitle(entity.getTitle());
        prescriptionTypeDto.setImgUrl(entity.getImgUrl());
        prescriptionTypeDto.setDescription(entity.getDescription());
        return prescriptionTypeDto;
    }

    public PrescriptionType convertToEntity(PrescriptionTypeDto dto, PrescriptionType prescriptionType) {
        if (dto == null) return null;
        prescriptionType.setName(dto.getName());
        prescriptionType.setTitle(dto.getTitle());
        prescriptionType.setImgUrl(dto.getImgUrl());
        prescriptionType.setDescription(dto.getDescription());
        return prescriptionType;
    }
}
