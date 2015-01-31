package com.expercise.controller.user.management;

import com.expercise.controller.BaseManagementController;
import com.expercise.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserListingController extends BaseManagementController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public ModelAndView listUsersForAdmin() {
        ModelAndView modelAndView = new ModelAndView("user/userList");
        modelAndView.addObject("users", userService.findAll());
        return modelAndView;
    }

}
