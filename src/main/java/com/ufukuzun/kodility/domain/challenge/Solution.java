package com.ufukuzun.kodility.domain.challenge;

import com.ufukuzun.kodility.enums.ProgrammingLanguage;

public class Solution {

    private ProgrammingLanguage programmingLanguage;

    private String solution;

    private String solutionTemplate;

    private String callPattern;

    public ProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getSolutionTemplate() {
        return solutionTemplate;
    }

    public void setSolutionTemplate(String solutionTemplate) {
        this.solutionTemplate = solutionTemplate;
    }

    public String getCallPattern() {
        return callPattern;
    }

    public void setCallPattern(String callPattern) {
        this.callPattern = callPattern;
    }
}
