package com.ufukuzun.kodility.testutils.builder;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;

public class SolutionBuilder  extends AbstractEntityBuilder<Solution, SolutionBuilder> {

    private Challenge challenge;

    private User user;

    private ProgrammingLanguage programmingLanguage;

    private String source;

    @Override
    public Solution doBuild() {
        Solution solution = new Solution();
        solution.setChallenge(challenge);
        solution.setUser(user);
        solution.setProgrammingLanguage(programmingLanguage);
        solution.setSolution(source);
        return solution;
    }

    public SolutionBuilder challenge(Challenge challenge) {
        this.challenge = challenge;
        return this;
    }

    public SolutionBuilder user(User user) {
        this.user = user;
        return this;
    }

    public SolutionBuilder programmingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
        return this;
    }

    public SolutionBuilder source(String source) {
        this.source = source;
        return this;
    }

}
