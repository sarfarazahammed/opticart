package com.sarfaraz.opticart.user.converter;

import com.sarfaraz.opticart.user.dto.power.LeftEyePowerDto;
import com.sarfaraz.opticart.user.dto.power.PowerDto;
import com.sarfaraz.opticart.user.dto.power.PrismDto;
import com.sarfaraz.opticart.user.dto.power.RightEyePowerDto;
import com.sarfaraz.opticart.user.entity.power.LeftEyePower;
import com.sarfaraz.opticart.user.entity.power.Power;
import com.sarfaraz.opticart.user.entity.power.RightEyePower;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PowerConverter implements Converter<Power, PowerDto> {

    private static LeftEyePowerDto getPower(LeftEyePower leftEyePower) {
        return LeftEyePowerDto.builder().
                sphere(leftEyePower.getSphere()).
                cylinder(leftEyePower.getCylinder()).
                axis(leftEyePower.getAxis()).
                add(leftEyePower.getAdd()).
                prism(new PrismDto(leftEyePower.getPrismHorizontal(), leftEyePower.getBaseHorizontal(), leftEyePower.getPrismVertical(), leftEyePower.getBaseVertical())).
                pupilDistance(leftEyePower.getPupliaryDistance()).
                build();
    }

    private static RightEyePower getPower(RightEyePowerDto powerDto) {
        PrismDto prismDto = new PrismDto();
        if (powerDto.getPrism() != null)
            prismDto = powerDto.getPrism();
        return RightEyePower.builder().
                sphere(powerDto.getSphere()).
                cylinder(powerDto.getCylinder()).
                axis(powerDto.getAxis()).
                add(powerDto.getAdd()).
                prismHorizontal(prismDto.getHorizontal()).
                prismVertical(prismDto.getVertical()).
                baseVertical(prismDto.getBaseVertical()).
                baseHorizontal(prismDto.getBaseHorizontal()).
                pupliaryDistance(powerDto.getPupilDistance()).
                build();
    }

    private static LeftEyePower getPower(LeftEyePowerDto powerDto) {
        PrismDto prismDto = new PrismDto();
        if (powerDto.getPrism() != null)
            prismDto = powerDto.getPrism();
        return LeftEyePower.builder().
                sphere(powerDto.getSphere()).
                cylinder(powerDto.getCylinder()).
                axis(powerDto.getAxis()).
                add(powerDto.getAdd()).
                prismHorizontal(prismDto.getHorizontal()).
                prismVertical(prismDto.getVertical()).
                baseVertical(prismDto.getBaseVertical()).
                baseHorizontal(prismDto.getBaseHorizontal()).
                pupliaryDistance(powerDto.getPupilDistance()).
                build();
    }

    private static RightEyePowerDto getPower(RightEyePower rightEyePower) {
        return RightEyePowerDto.builder().
                sphere(rightEyePower.getSphere()).
                cylinder(rightEyePower.getCylinder()).
                axis(rightEyePower.getAxis()).
                add(rightEyePower.getAdd()).
                prism(new PrismDto(rightEyePower.getPrismHorizontal(), rightEyePower.getBaseHorizontal(), rightEyePower.getPrismVertical(), rightEyePower.getBaseVertical())).
                pupilDistance(rightEyePower.getPupliaryDistance()).
                build();
    }

    @Override
    public Power convertToEntity(PowerDto dto) {
        if (dto == null) return null;
        return Power.builder()
                .id(UUID.randomUUID().toString())
                .leftEyePower(getPower(dto.getLeftEye()))
                .rightEyePower(getPower(dto.getRightEye()))
                .baseCurve(dto.getBaseCurve())
                .diameter(dto.getDiameter())
                .build();
    }

    @Override
    public PowerDto convertToDto(Power entity) {
        if (entity == null) return new PowerDto();
        LeftEyePower leftEyePower = entity.getLeftEyePower();
        RightEyePower rightEyePower = entity.getRightEyePower();
        return PowerDto.builder().
                id(entity.getId()).
                leftEye(getPower(leftEyePower)).
                rightEye(getPower(rightEyePower)).
                baseCurve(entity.getBaseCurve()).
                diameter(entity.getDiameter()).
                build();
    }

    public Power convertToEntity(PowerDto dto, Power power) {
        if (dto == null) return null;
        return Power.builder()
                .id(dto.getId())
                .leftEyePower(getPower(dto.getLeftEye()))
                .rightEyePower(getPower(dto.getRightEye()))
                .baseCurve(dto.getBaseCurve())
                .diameter(dto.getDiameter())
                .build();
    }

}
