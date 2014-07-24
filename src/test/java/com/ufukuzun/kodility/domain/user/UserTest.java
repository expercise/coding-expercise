package com.ufukuzun.kodility.domain.user;

import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.testutils.builder.UserBuilder;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void shouldReturnHyphenAsFavoriteProgrammingLanguageWhenUserHasNoFavoriteLanguage() {
        User user = new UserBuilder().id(1L).programmingLanguage(null).build();
        assertThat(user.getFavoriteProgLang(), equalTo("-"));
    }

    @Test
    public void shouldReturnFavoriteProgrammingLanguageWhenUserHasOneOfThem() {
        User pythonicUser = new UserBuilder().id(1L).programmingLanguage(ProgrammingLanguage.Python).build();
        User javaScripterUser = new UserBuilder().id(2L).programmingLanguage(ProgrammingLanguage.JavaScript).build();

        assertThat(pythonicUser.getFavoriteProgLang(), equalTo("Python"));
        assertThat(javaScripterUser.getFavoriteProgLang(), equalTo("JavaScript"));
    }

}