package com.expercise.utils.validation;

import com.expercise.controller.user.model.PasswordModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, PasswordModel> {

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswordModel passwordModel, ConstraintValidatorContext context) {
        String password = passwordModel.getPassword();
        String passwordRetype = passwordModel.getPasswordRetype();

        if (StringUtils.isBlank(password) || StringUtils.isBlank(passwordRetype)) {
            return false;
        }

        if (!password.equals(passwordRetype)) {
            context.buildConstraintViolationWithTemplate("passwordMatchError").addPropertyNode("passwordRetype").addConstraintViolation();
            return false;
        }

        return true;
    }

}
