package com.expercise.service.user;

import com.expercise.dao.user.UserDao;
import com.expercise.domain.user.User;
import com.expercise.enums.Lingo;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.testutils.builder.UserBuilder;
import com.expercise.utils.PasswordEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldSaveNewUserWithHashedPassword() {
        User user = new UserBuilder().firstName("mahmut").lastName("kemal")
                .email("mail@expercise.com").lingo(Lingo.Turkish).password("password")
                .programmingLanguage(ProgrammingLanguage.Python).build();

        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");

        userService.saveUser(user);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).save(userCaptor.capture());

        assertThat(user.getPassword(), equalTo("hashedPassword"));
    }

}
