package com.kodility.domain.challenge;

import com.kodility.domain.level.Level;
import com.kodility.domain.theme.Theme;
import com.kodility.enums.Lingo;
import com.kodility.testutils.builder.ChallengeBuilder;
import com.kodility.testutils.builder.LevelBuilder;
import com.kodility.testutils.builder.ThemeBuilder;
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

    @Test
    public void shouldGetTitleByLingo() {
        Challenge challenge = new Challenge();
        challenge.getTitles().put(Lingo.English, "Title");
        challenge.getTitles().put(Lingo.Turkish, "Başlık");

        assertThat(challenge.getTitleFor(Lingo.English.getShortName()), equalTo("Title"));
        assertThat(challenge.getTitleFor(Lingo.Turkish.getShortName()), equalTo("Başlık"));
    }

    @Test
    public void shouldReturnThemeIdIfChallengeHaveLevelAndLevelHaveTheme() {
        Level level = new LevelBuilder().buildWithRandomId();
        Theme theme = new ThemeBuilder().levels(level).buildWithRandomId();
        Challenge challenge = new ChallengeBuilder().level(level).buildWithRandomId();

        assertThat(challenge.getThemeId(), equalTo(theme.getId().toString()));
    }

    @Test
    public void shouldReturnOthersAsThemeIdIfChallengeHaveLevelButLevelHaveNotTheme() {
        Level level = new LevelBuilder().buildWithRandomId();
        Challenge challenge = new ChallengeBuilder().level(level).buildWithRandomId();

        assertThat(challenge.getThemeId(), equalTo("others"));
    }

    @Test
    public void shouldReturnOthersAsThemeIdIfChallengeHaveNotLevel() {
        Challenge challenge = new ChallengeBuilder().buildWithRandomId();

        assertThat(challenge.getThemeId(), equalTo("others"));
    }

}
