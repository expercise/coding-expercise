package com.kodility.controller.user;

import com.kodility.controller.user.model.EmailRequestModel;
import com.kodility.controller.user.model.ForgotMyPasswordResponse;
import com.kodility.domain.user.User;
import com.kodility.service.user.ForgotMyPasswordService;
import com.kodility.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    @ResponseBody
    public String resetPasswordIndex(@RequestParam(value = "token", required = true) String token) {
        //todo:batu: do it
        return "not yet implemented";
    }


}
