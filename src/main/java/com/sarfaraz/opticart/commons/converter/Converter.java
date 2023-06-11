package com.sarfaraz.opticart.commons.converter;

public interface Converter<S, T> {
    S convertToEntity(T dto);

    T convertToDto(S entity);
}
