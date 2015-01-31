package com.expercise.service.challenge.model;

import com.expercise.domain.challenge.Challenge;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.InterpreterResult;

public class ChallengeEvaluationContext {

    private Challenge challenge;

    private String source;

    private InterpreterResult interpreterResult;

    private ProgrammingLanguage language;

    private SolutionValidationResult solutionValidationResult;

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
}
