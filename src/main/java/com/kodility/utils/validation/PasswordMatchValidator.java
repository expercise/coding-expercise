package com.kodility.utils.validation;

import com.kodility.controller.user.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserModel> {

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserModel userModel, ConstraintValidatorContext context) {
        String password = userModel.getPassword();
        String passwordRetype = userModel.getPasswordRetype();

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
