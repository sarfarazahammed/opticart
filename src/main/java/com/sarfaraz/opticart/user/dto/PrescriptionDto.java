package com.sarfaraz.opticart.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarfaraz.opticart.user.dto.power.PowerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionDto {

    private String id;
    private String prescriptionTypeId;
    private String prescriptionTypeName;
    private PowerDto power;

    private LocalDateTime createdDate;

}
