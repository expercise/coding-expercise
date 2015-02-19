package com.expercise.domain.challenge;

import com.expercise.domain.level.Level;
import com.expercise.domain.theme.Theme;
import com.expercise.enums.Lingo;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.LevelBuilder;
import com.expercise.testutils.builder.ThemeBuilder;
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
    public void shouldReturnThemeUrlIfChallengeHaveLevelAndLevelHaveTheme() {
        Level level = new LevelBuilder().buildWithRandomId();
        Theme theme = new ThemeBuilder().addName(Lingo.English, "Theme Name").levels(level).buildWithRandomId();
        Challenge challenge = new ChallengeBuilder().level(level).buildWithRandomId();

        assertThat(challenge.getThemeBookmarkableUrl("en"), equalTo("/themes/" + theme.getId().toString() + "/theme-name"));
    }

    @Test
    public void shouldReturnOtherChallengesAsThemeUrlIfChallengeHaveLevelButLevelHaveNotTheme() {
        Level level = new LevelBuilder().buildWithRandomId();
        Challenge challenge = new ChallengeBuilder().level(level).buildWithRandomId();

        assertThat(challenge.getThemeBookmarkableUrl("en"), equalTo("/themes/other-challenges"));
    }

    @Test
    public void shouldReturnOtherChallengesAsThemeUrlIfChallengeHaveNotLevel() {
        Challenge challenge = new ChallengeBuilder().buildWithRandomId();

        assertThat(challenge.getThemeBookmarkableUrl("tr"), equalTo("/themes/other-challenges"));
    }

}
