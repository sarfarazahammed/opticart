package com.sarfaraz.opticart.user.converter;

import com.sarfaraz.opticart.user.dto.AddressDto;
import com.sarfaraz.opticart.user.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter implements Converter<Address, AddressDto> {
    @Override
    public Address convertToEntity(AddressDto dto) {
        if (dto == null) return null;
        Address address = new Address();
        address.setName(dto.getName());
        address.setAddress(dto.getAddress());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setZipcode(dto.getZipcode());
        address.setCountryCallingCode(dto.getCountryCallingCode());
        address.setPhoneNumber(dto.getPhoneNumber());
        address.setDefault(dto.isDefault());
        address.setTag(dto.getTag());
        return address;
    }

    @Override
    public AddressDto convertToDto(Address entity) {
        if (entity == null) return null;
        AddressDto addressDto = new AddressDto();

        addressDto.setId(entity.getId());
        addressDto.setName(entity.getName());
        addressDto.setAddress(entity.getAddress());
        addressDto.setCity(entity.getCity());
        addressDto.setState(entity.getState());
        addressDto.setCountry(entity.getCountry());
        addressDto.setZipcode(entity.getZipcode());
        addressDto.setCountryCallingCode(entity.getCountryCallingCode());
        addressDto.setPhoneNumber(entity.getPhoneNumber());
        addressDto.setDefault(entity.isDefault());
        addressDto.setTag(entity.getTag());

        return addressDto;
    }

    public Address convertToEntity(AddressDto dto, Address address) {
        address.setName(dto.getName());
        address.setAddress(dto.getAddress());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setZipcode(dto.getZipcode());
        address.setCountryCallingCode(dto.getCountryCallingCode());
        address.setPhoneNumber(dto.getPhoneNumber());
        address.setDefault(dto.isDefault());
        address.setTag(dto.getTag());
        return address;
    }

}
