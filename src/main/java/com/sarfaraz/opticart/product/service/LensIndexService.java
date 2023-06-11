package com.sarfaraz.opticart.product.service;

import com.sarfaraz.opticart.product.dto.LensIndexDto;

import java.util.List;

public interface LensIndexService {

    List<LensIndexDto> getLensIndices();

    LensIndexDto getLensIndex(double index);

    LensIndexDto addLensIndex(LensIndexDto lensIndexDto);

    LensIndexDto updateLensIndexDto(LensIndexDto lensIndexDto);

}
