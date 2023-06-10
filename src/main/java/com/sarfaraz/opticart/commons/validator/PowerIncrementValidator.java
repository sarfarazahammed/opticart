package com.sarfaraz.opticart.commons.validator;

import com.sarfaraz.opticart.commons.annotation.PowerIncrement;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PowerIncrementValidator implements ConstraintValidator<PowerIncrement, Float> {
    private float minValue;
    private float maxValue;

    @Override
    public void initialize(PowerIncrement powerIncrement) {
        minValue = powerIncrement.min();
        maxValue = powerIncrement.max();
    }

    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {
        return (value <= maxValue && value >= minValue) && value % 0.25 == 0;
    }
}
