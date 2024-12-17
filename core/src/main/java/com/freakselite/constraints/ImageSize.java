package com.freakselite.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageSizeValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageSize {
    String message() default "Invalid image size";

    // 2MB
    long maxSize() default 2 * 1024 * 1024;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
