package com.expercise.testutils.builder;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.Solution;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.utils.TextUtils;

import java.util.Date;

public class SolutionBuilder extends BaseEntityBuilder<Solution, SolutionBuilder> {

    private Challenge challenge;

    private User user;

    private ProgrammingLanguage programmingLanguage = ProgrammingLanguage.Python2;

    private String solution = TextUtils.randomAlphabetic(10);

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
