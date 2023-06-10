package com.sarfaraz.opticart.commons.annotation;

import com.sarfaraz.opticart.commons.validator.PowerIncrementValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PowerIncrementValidator.class)
public @interface PowerIncrement {
    String message() default "Value must fall in range of -20 to +10 and should be divisible by 0.25";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    float min() default -10.0f;

    float max() default +20.0f;
}
