package com.sarfaraz.opticart.user.dto;

import com.sarfaraz.opticart.security.enums.AddressTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private String id;
    @NotNull
    private String name;
    @NotNull
    private String countryCallingCode;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String country;
    @NotNull
    private String zipcode;
    private boolean isDefault;
    private AddressTag tag = AddressTag.OTHER;
}
