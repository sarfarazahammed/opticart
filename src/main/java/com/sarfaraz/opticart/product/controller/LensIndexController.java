package com.sarfaraz.opticart.product.controller;

import com.sarfaraz.opticart.commons.dto.ResponseDto;
import com.sarfaraz.opticart.commons.dto.SuccessResponseDto;
import com.sarfaraz.opticart.product.dto.LensIndexDto;
import com.sarfaraz.opticart.product.service.LensIndexService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lens/index")
public class LensIndexController {

    private final LensIndexService lensIndexService;

    public LensIndexController(LensIndexService lensIndexService) {
        this.lensIndexService = lensIndexService;
    }

    @GetMapping("all")
    public ResponseDto getLensIndex() {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("List of lens indices")
                .data(lensIndexService.getLensIndices()).build();
    }

    @GetMapping("")
    public ResponseDto getLensIndexDetail(@RequestParam("index") double index) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Details of lens index")
                .data(lensIndexService.getLensIndex(index)).build();
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseDto addLensIndex(@RequestBody LensIndexDto lensIndexDto) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Lens index added successfully")
                .data(lensIndexService.addLensIndex(lensIndexDto)).build();
    }

    @PutMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseDto updateLensIndex(@RequestBody LensIndexDto lensIndexDto) {
        return SuccessResponseDto.builder()
                .code(HttpStatus.OK)
                .message("Lens index updated successfully")
                .data(lensIndexService.updateLensIndexDto(lensIndexDto)).build();
    }

}
