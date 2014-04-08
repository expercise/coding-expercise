package com.ufukuzun.kodility.domain.challenge;

import com.ufukuzun.kodility.enums.Lingo;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ChallengeTest {

    @Test
    public void shouldGetDescriptionByLingo() {
        Challenge challenge = new Challenge();
        challenge.getDescriptions().put(Lingo.English, "Description");
        challenge.getDescriptions().put(Lingo.Turkish, "Açıklama");

        assertThat(challenge.getDescriptionFor(Lingo.English.getShortName()), equalTo("Description"));
        assertThat(challenge.getDescriptionFor(Lingo.Turkish.getShortName()), equalTo("Açıklama"));
    }

}
