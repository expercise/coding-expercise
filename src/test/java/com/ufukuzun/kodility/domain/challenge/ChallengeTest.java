package com.ufukuzun.kodility.domain.challenge;

import com.ufukuzun.kodility.enums.Lingo;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ChallengeTest {

    @Test
    public void shouldGetAvailableLanguagesForChallenge() {
        Solution solution = new Solution();
        solution.setProgrammingLanguage(ProgrammingLanguage.JavaScript);

        Challenge challenge = new Challenge();
        challenge.setSolutions(Arrays.asList(solution));

        List<ProgrammingLanguage> availableLanguages = challenge.getProgrammingLanguages();

        assertThat(availableLanguages, hasSize(1));
        assertThat(availableLanguages, hasItem(ProgrammingLanguage.JavaScript));
    }

    @Test
    public void shouldGetDescriptionByLingo() {
        Challenge challenge = new Challenge();
        challenge.getDescriptions().put(Lingo.English, "Description");
        challenge.getDescriptions().put(Lingo.Turkish, "Açıklama");

        assertThat(challenge.getDescriptionFor(Lingo.English.getShortName()), equalTo("Description"));
        assertThat(challenge.getDescriptionFor(Lingo.Turkish.getShortName()), equalTo("Açıklama"));
    }

    @Test
    public void shouldGetSolutionForProgrammingLanguage() {
        Solution solutionWithJavaScript = new Solution();
        solutionWithJavaScript.setProgrammingLanguage(ProgrammingLanguage.JavaScript);

        Solution solutionWithPython = new Solution();
        solutionWithPython.setProgrammingLanguage(ProgrammingLanguage.Python);

        Challenge challenge = new Challenge();
        challenge.setSolutions(Arrays.asList(solutionWithJavaScript, solutionWithPython));

        assertThat(challenge.getSolutionFor(ProgrammingLanguage.JavaScript), equalTo(solutionWithJavaScript));
        assertThat(challenge.getSolutionFor(ProgrammingLanguage.Python), equalTo(solutionWithPython));
    }

}
