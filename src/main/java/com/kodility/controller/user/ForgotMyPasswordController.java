package com.kodility.controller.user;

import com.kodility.controller.RedirectUtil;
import com.kodility.controller.user.model.EmailRequestModel;
import com.kodility.controller.user.model.ForgotMyPasswordResponse;
import com.kodility.controller.user.model.PasswordResetModel;
import com.kodility.domain.token.Token;
import com.kodility.domain.user.User;
import com.kodility.service.user.ForgotMyPasswordService;
import com.kodility.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/forgotMyPassword")
public class ForgotMyPasswordController {

    @Autowired
    private ForgotMyPasswordService forgotMyPasswordService;

    @Autowired
    private UserService userService;

    @RequestMapping
    public ModelAndView index() {
        return new ModelAndView("/user/forgotMyPassword");
    }

    @RequestMapping(value = "/sendForgotMyPasswordEmail", method = RequestMethod.POST)
    @ResponseBody
    public ForgotMyPasswordResponse sendForgotMyPasswordEmail(@RequestBody EmailRequestModel emailRequestModel) {
        User user = userService.findByEmail(emailRequestModel.getEmail());
        if (user == null) {
            return ForgotMyPasswordResponse.failedResponse();
        }
        forgotMyPasswordService.sendResetEmail(user);
        return ForgotMyPasswordResponse.successResponse();
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView resetPasswordIndex(@RequestParam(value = "token", required = true) String token) {
        if (forgotMyPasswordService.validPasswordResetToken(token)) {
            ModelAndView modelAndView = new ModelAndView("/user/passwordReset");
            PasswordResetModel passwordResetModel = new PasswordResetModel();
            passwordResetModel.setToken(token);
            modelAndView.addObject("passwordResetModel", passwordResetModel);
            return modelAndView;
        }
        return RedirectUtil.redirectHome();
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView resetPassword(@ModelAttribute @Valid PasswordResetModel passwordResetModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/user/passwordReset");
        }
        String token = passwordResetModel.getToken();
        Token foundToken = forgotMyPasswordService.getForgotMyPasswordTokenFor(token);
        if (foundToken == null) {
            forgotMyPasswordService.deleteToken(token);
            return RedirectUtil.redirectHome();
        }
        processForValidToken(passwordResetModel, foundToken.getUser());
        return RedirectUtil.redirectLoginFor("resetpasswordsuccess");
    }

    private void processForValidToken(PasswordResetModel passwordResetModel, User user) {
        String password = passwordResetModel.getPasswordModel().getPassword();
        user.setPassword(password);
        userService.saveUser(user);
        forgotMyPasswordService.deleteToken(passwordResetModel.getToken());
    }

}
