package com.sarfaraz.opticart.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LensIndexDto {
    private String id;
    private double index;
    private double sphMinPos;
    private double sphMinNeg;
    private double sphMaxPos;
    private double sphMaxNeg;
    private double cylMinPos;
    private double cylMinNeg;
    private double cylMaxPos;
    private double cylMaxNeg;
    private String features;
    private String imgUrl;
}
