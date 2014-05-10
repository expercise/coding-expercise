package com.ufukuzun.kodility.utils.validator;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public abstract class AbstractValidator<T> {

    public abstract void validate(T object, BindingResult bindingResult);

    protected void validateField(boolean condition, BindingResult bindingResult, String objectName, String field, Object value, String[] codes) {
        if (!condition) {
            bindingResult.addError(new FieldError(objectName, field, value, false, codes, null, null));
        }
    }

}
