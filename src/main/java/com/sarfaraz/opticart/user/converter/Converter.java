package com.sarfaraz.opticart.user.converter;

public interface Converter<S, T> {
    S convertToEntity(T dto);

    T convertToDto(S entity);
}
