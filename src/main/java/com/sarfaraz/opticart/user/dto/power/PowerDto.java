package com.sarfaraz.opticart.user.dto.power;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PowerDto {
    private String id;
    private LeftEyePowerDto leftEye;
    private RightEyePowerDto rightEye;
    private float baseCurve;
    private float diameter;
}
