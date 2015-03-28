package com.expercise.domain.user;

import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void shouldReturnBookmarkableUrlByIdAndFullName() {
        User user = new UserBuilder().id(1989L).firstName("Ufuk").lastName("Uzun").build();

        assertThat(user.getBookmarkableUrl(), equalTo("/user/1989/ufuk-uzun"));
    }

}