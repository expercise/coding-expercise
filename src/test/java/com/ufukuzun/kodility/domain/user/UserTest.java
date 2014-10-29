package com.ufukuzun.kodility.domain.user;

import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.testutils.builder.UserBuilder;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void shouldReturnEmptyAsFavoriteProgrammingLanguageWhenUserHasNoFavoriteLanguage() {
        User user = new UserBuilder().id(1L).programmingLanguage(null).build();
        assertThat(user.getFavoriteProgrammingLanguage(), equalTo(""));
    }

    @Test
    public void shouldReturnFavoriteProgrammingLanguageWhenUserHasOneOfThem() {
        User pythonicUser = new UserBuilder().id(1L).programmingLanguage(ProgrammingLanguage.Python).build();
        User javaScripterUser = new UserBuilder().id(2L).programmingLanguage(ProgrammingLanguage.JavaScript).build();

        assertThat(pythonicUser.getFavoriteProgrammingLanguage(), equalTo("Python"));
        assertThat(javaScripterUser.getFavoriteProgrammingLanguage(), equalTo("JavaScript"));
    }

}