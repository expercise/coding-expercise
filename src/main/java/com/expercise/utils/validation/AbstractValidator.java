package com.expercise.utils.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public abstract class AbstractValidator<T> {

    public abstract void validate(T object, BindingResult bindingResult);

    protected boolean validateField(boolean condition, BindingResult bindingResult, String objectName, String field, Object value, String[] codes) {
        if (!condition) {
            bindingResult.addError(new FieldError(objectName, field, value, false, codes, null, null));
            return false;
        } else {
            return true;
        }
    }

    protected void addError(BindingResult bindingResult, String objectName, String[] codes) {
        bindingResult.addError(new ObjectError(objectName, codes, null, null));
    }

}
