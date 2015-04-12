package com.expercise.service.challenge.model;

import com.expercise.domain.challenge.Challenge;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.InterpreterResult;
import com.expercise.interpreter.TestCaseWithResult;

import java.util.ArrayList;
import java.util.List;

public class ChallengeEvaluationContext {

    private Challenge challenge;

    private String source;

    private InterpreterResult interpreterResult;

    private ProgrammingLanguage language;

    private SolutionValidationResult solutionValidationResult;

    private List<TestCaseWithResult> testCaseWithResults = new ArrayList<>();

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public InterpreterResult getInterpreterResult() {
        return interpreterResult;
    }

    public void setInterpreterResult(InterpreterResult interpreterResult) {
        this.interpreterResult = interpreterResult;
    }

    public void setLanguage(ProgrammingLanguage language) {
        this.language = language;
    }

    public ProgrammingLanguage getLanguage() {
        return language;
    }

    public SolutionValidationResult getSolutionValidationResult() {
        return solutionValidationResult;
    }

    public void setSolutionValidationResult(SolutionValidationResult solutionValidationResult) {
        this.solutionValidationResult = solutionValidationResult;
    }

    public List<TestCaseWithResult> getTestCaseWithResults() {
        return testCaseWithResults;
    }

    public void addTestCaseWithResult(TestCaseWithResult testCaseWithResult) {
        getTestCaseWithResults().add(testCaseWithResult);
    }

    public void decideInterpreterResult() {
        boolean passedAllGivenTests = getTestCaseWithResults().stream().noneMatch(TestCaseWithResult::isFailed);
        if (passedAllGivenTests) {
            setInterpreterResult(InterpreterResult.createSuccessResult());
        } else {
            setInterpreterResult(InterpreterResult.createFailedResult());
        }
    }

    public boolean isChallengeCompleted() {
        int allChallengeTestCaseCount = challenge.getTestCases().size();
        int underTestCount = getTestCaseWithResults().size();
        return getInterpreterResult().isSuccess() && allChallengeTestCaseCount == underTestCount;
    }

}
