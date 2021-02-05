package org.iushu.validation.validators;

import org.iushu.validation.beans.Address;
import org.iushu.validation.beans.Civilian;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @see org.springframework.validation.Validator
 * @see org.springframework.validation.ValidationUtils
 * @author iuShu
 * @since 1/25/21
 */
public class CivilianValidator implements Validator {

    private AddressValidator dependedValidator;

    public CivilianValidator(AddressValidator validator) {
        this.dependedValidator = validator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Civilian.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        Civilian civilian = (Civilian) target;
        if (civilian.getAge() < 0)
            errors.rejectValue("age", "Negative Value");
        else if (civilian.getAge() > 110)
            errors.rejectValue("age", "Too Darn Old");

        // validation dependency (push & pop)
        try {
            errors.pushNestedPath(Address.class.getSimpleName().toLowerCase());
            ValidationUtils.invokeValidator(this.dependedValidator, civilian.getHome(), errors);
        } finally {
            errors.popNestedPath();
        }
    }
}
