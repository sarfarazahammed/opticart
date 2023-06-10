package com.sarfaraz.opticart.user.dto.power;

import com.sarfaraz.opticart.commons.annotation.PowerIncrement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LeftEyePowerDto {
    @PowerIncrement(min = -6.0f, max = 6.0f, message = "Value must fall in range of -6 to +6")
    private float cylinder;
    @PowerIncrement
    private float sphere;
    @PowerIncrement
    private float add;
    @Min(value = 1, message = "Value must fall in range of 1 - 180")
    @Max(value = 180, message = "Value must fall in range of 1 - 180")
    private int axis;
    private PrismDto prism;
    @Min(value = 54, message = "Value must fall in range of 54 - 78")
    @Max(value = 78, message = "Value must fall in range of 54 - 78")
    private int pupilDistance;

}
