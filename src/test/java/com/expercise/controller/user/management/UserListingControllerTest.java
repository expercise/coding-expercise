package com.expercise.controller.user.management;

import com.expercise.domain.user.User;
import com.expercise.service.user.UserService;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserListingControllerTest {

    @InjectMocks
    private UserListingController controller;

    @Mock
    private UserService userService;

    @Test
    public void shouldGetAllUsers() {
        User user0 = new UserBuilder().buildWithRandomId();
        User user1 = new UserBuilder().buildWithRandomId();

        when(userService.findAll()).thenReturn(Arrays.asList(user0, user1));

        ModelAndView modelAndView = controller.listUsersForAdmin();

        List<User> users = (List<User>) modelAndView.getModel().get("users");

        assertThat(users.size(), equalTo(2));
        assertThat(users, hasItems(user0, user1));
        assertThat(modelAndView.getViewName(), equalTo("user/userList"));
    }

}