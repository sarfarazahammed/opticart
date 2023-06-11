package com.sarfaraz.opticart.product.service;

import com.sarfaraz.opticart.product.converter.LensIndexConverter;
import com.sarfaraz.opticart.product.dto.LensIndexDto;
import com.sarfaraz.opticart.product.entity.LensIndex;
import com.sarfaraz.opticart.product.repo.LensIndexRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LensIndexServiceImpl implements LensIndexService {

    private final LensIndexRepo lensIndexRepo;
    private final LensIndexConverter lensIndexConverter;

    public LensIndexServiceImpl(LensIndexRepo lensIndexRepo, LensIndexConverter lensIndexConverter) {
        this.lensIndexRepo = lensIndexRepo;
        this.lensIndexConverter = lensIndexConverter;
    }

    @Override
    public List<LensIndexDto> getLensIndices() {
        return lensIndexRepo.findAll().stream().map(lensIndexConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public LensIndexDto getLensIndex(double index) {
        return lensIndexConverter.convertToDto(lensIndexRepo.findByIndex(index).orElseThrow(() -> new IllegalArgumentException("Lens index not found")));
    }

    @Override
    public LensIndexDto addLensIndex(LensIndexDto lensIndexDto) {
        return lensIndexConverter.convertToDto(lensIndexRepo.save(lensIndexConverter.convertToEntity(lensIndexDto)));
    }

    @Override
    public LensIndexDto updateLensIndexDto(LensIndexDto lensIndexDto) {
        LensIndex lensIndex = lensIndexRepo.findById(lensIndexDto.getId()).orElseThrow(() -> new IllegalArgumentException("Lens index not found"));
        return lensIndexConverter.convertToDto(lensIndexRepo.save(lensIndexConverter.convertToEntity(lensIndex, lensIndexDto)));
    }
}
