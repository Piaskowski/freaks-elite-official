package com.freakselite.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageSizeValidation implements ConstraintValidator<ImageSize, MultipartFile> {

    private long maxSize;

    @Override
    public void initialize(ImageSize constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return file == null || file.getSize() <=
                maxSize;
    }
}
