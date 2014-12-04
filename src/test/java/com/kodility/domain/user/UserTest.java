package com.kodility.domain.user;

import com.kodility.enums.ProgrammingLanguage;
import com.kodility.testutils.builder.UserBuilder;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void shouldReturnEmptyAsFavoriteProgrammingLanguageWhenUserHasNoFavoriteLanguage() {
        User user = new UserBuilder().programmingLanguage(null).build();

        assertThat(user.getFavoriteProgrammingLanguage(), equalTo(""));
    }

    @Test
    public void shouldReturnFavoriteProgrammingLanguageWhenUserHasOneOfThem() {
        User pythonicUser = new UserBuilder().programmingLanguage(ProgrammingLanguage.Python).build();
        User javaScripterUser = new UserBuilder().programmingLanguage(ProgrammingLanguage.JavaScript).build();

        assertThat(pythonicUser.getFavoriteProgrammingLanguage(), equalTo("Python"));
        assertThat(javaScripterUser.getFavoriteProgrammingLanguage(), equalTo("JavaScript"));
    }

}