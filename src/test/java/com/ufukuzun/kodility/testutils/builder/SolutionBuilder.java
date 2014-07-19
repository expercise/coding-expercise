package com.ufukuzun.kodility.testutils.builder;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;

import java.util.Date;

public class SolutionBuilder  extends AbstractEntityBuilder<Solution, SolutionBuilder> {

    private Challenge challenge;

    private User user;

    private ProgrammingLanguage programmingLanguage = ProgrammingLanguage.Python;

    private String solution;

    private Date createDate = new Date();

    @Override
    protected Solution doBuild() {
        Solution newSolution = new Solution();
        newSolution.setChallenge(challenge);
        newSolution.setUser(user);
        newSolution.setProgrammingLanguage(programmingLanguage);
        newSolution.setSolution(solution);
        newSolution.setCreateDate(createDate);
        return newSolution;
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

    public SolutionBuilder solution(String solution) {
        this.solution = solution;
        return this;
    }

    public SolutionBuilder createDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

}
