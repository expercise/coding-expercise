package com.ufukuzun.kodility.controller.challenge.model;

import com.ufukuzun.kodility.enums.ProgrammingLanguage;

public class SolutionFromUser {

    private String solution;

    private String language;

    private Long challengeId;

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public ProgrammingLanguage getProgrammingLanguage() {
        return ProgrammingLanguage.getLanguage(language).get();
    }

}
