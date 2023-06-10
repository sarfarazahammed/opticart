package com.sarfaraz.opticart.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarfaraz.opticart.user.enums.Gender;
import com.sarfaraz.opticart.user.enums.MemberTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {
    private String id;
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
    private MemberTag tag;
    private Set<PrescriptionDto> prescriptions = new HashSet<>();
}
