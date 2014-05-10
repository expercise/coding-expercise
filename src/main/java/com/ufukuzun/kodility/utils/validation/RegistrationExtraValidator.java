package com.ufukuzun.kodility.utils.validation;

import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class RegistrationExtraValidator extends AbstractValidator<User> {

    @Autowired
    private UserService userService;

    @Override
    public void validate(User user, BindingResult bindingResult) {
        validateWhetherEmailAddressIsAlreadyRegistered(user, bindingResult);
    }

    private void validateWhetherEmailAddressIsAlreadyRegistered(User user, BindingResult bindingResult) {
        validateField(userService.emailNotRegisteredYet(user.getEmail()), bindingResult, "user", "email", user.getEmail(), new String[]{"NotUnique.user.email"});
    }

}
