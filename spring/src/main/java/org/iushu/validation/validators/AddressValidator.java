package org.iushu.validation.validators;

import org.iushu.validation.beans.Address;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @see Validator
 * @see ValidationUtils
 * @author iuShu
 * @since 1/25/21
 */
public class AddressValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Address.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "country", "country.empty");
        ValidationUtils.rejectIfEmpty(errors, "province", "province.empty");
        ValidationUtils.rejectIfEmpty(errors, "city", "city.empty");
    }
}
