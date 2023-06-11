package com.sarfaraz.opticart.product.converter;

import com.sarfaraz.opticart.commons.converter.Converter;
import com.sarfaraz.opticart.product.dto.LensIndexDto;
import com.sarfaraz.opticart.product.entity.LensIndex;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LensIndexConverter implements Converter<LensIndex, LensIndexDto> {
    @Override
    public LensIndex convertToEntity(LensIndexDto dto) {
        return new LensIndex(
                UUID.randomUUID().toString(), dto.getIndex(),
                dto.getSphMinPos(), dto.getSphMinNeg(), dto.getSphMaxPos(), dto.getSphMaxNeg(),
                dto.getCylMinPos(), dto.getCylMinNeg(), dto.getCylMaxPos(), dto.getCylMaxNeg(),
                dto.getFeatures(), dto.getImgUrl());
    }

    @Override
    public LensIndexDto convertToDto(LensIndex entity) {
        return new LensIndexDto(
                entity.getId(), entity.getIndex(),
                entity.getSphMinPos(), entity.getSphMinNeg(), entity.getSphMaxPos(), entity.getSphMaxNeg(),
                entity.getCylMinPos(), entity.getCylMinNeg(), entity.getCylMaxPos(), entity.getCylMaxNeg(),
                entity.getFeatures(), entity.getImgUrl());
    }

    public LensIndex convertToEntity(LensIndex lensIndex, LensIndexDto lensIndexDto) {
        if (lensIndexDto == null) return null;
        lensIndex.setFeatures(lensIndexDto.getFeatures());
        lensIndex.setSphMinNeg(lensIndexDto.getSphMinNeg());
        lensIndex.setSphMaxNeg(lensIndexDto.getSphMaxNeg());
        lensIndex.setSphMinPos(lensIndexDto.getSphMinPos());
        lensIndex.setSphMaxPos(lensIndexDto.getSphMaxPos());
        lensIndex.setCylMinPos(lensIndexDto.getCylMinPos());
        lensIndex.setCylMaxPos(lensIndexDto.getCylMaxPos());
        lensIndex.setCylMinNeg(lensIndexDto.getCylMinNeg());
        lensIndex.setCylMaxNeg(lensIndexDto.getCylMaxNeg());
        return lensIndex;
    }
}
