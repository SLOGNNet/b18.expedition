package com.bridge18.expedition.dto.v1;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUniqueNumber.NumberValidator.class)
@Documented
public @interface CheckUniqueNumber {

    String message() default "Number in use. Please enter unique number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class NumberValidator implements ConstraintValidator<CheckUniqueNumber, String> {
        public void initialize(CheckUniqueNumber constraint) {
        }

        public boolean isValid(String number, ConstraintValidatorContext context) {

            return false;
        }
    }
}
