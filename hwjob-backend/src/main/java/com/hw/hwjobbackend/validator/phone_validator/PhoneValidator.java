package com.hw.hwjobbackend.validator.phone_validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneConstraint, String> {

    // Cho phép: 0xxxxxxxxx (10 số, bắt đầu 03/05/07/08/09),
    // hoặc +84/84 rồi đến 9 số tiếp theo (bắt đầu 3/5/7/8/9)
    private static final String VN_PHONE_REGEX =
            "^(0[35789][0-9]{8}|(?:\\+?84)[35789][0-9]{8})$";

    @Override
    public void initialize(PhoneConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        String phone = value.trim();
        if (phone.isEmpty()) return false;
        return phone.matches(VN_PHONE_REGEX);
    }
}
