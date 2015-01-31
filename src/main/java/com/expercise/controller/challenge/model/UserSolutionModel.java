package com.expercise.controller.challenge.model;

import com.expercise.domain.challenge.Solution;
import com.expercise.utils.DateUtils;

import java.io.Serializable;

public class UserSolutionModel implements Serializable {

    private String programmingLanguage;

    private String languageShortName;

    private String solution;

    private String solutionDate;

    private UserSolutionModel() {
    }

    public static UserSolutionModel createFrom(Solution solution) {
        UserSolutionModel userSolutionModel = new UserSolutionModel();
        userSolutionModel.setProgrammingLanguage(solution.getProgrammingLanguage().name());
        userSolutionModel.setLanguageShortName(solution.getProgrammingLanguage().getShortName());
        userSolutionModel.setSolution(solution.getSolution());
        userSolutionModel.setSolutionDate(DateUtils.formatDateTimeWithNamedMonth(solution.getCreateDate()));
        return userSolutionModel;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getSolutionDate() {
        return solutionDate;
    }

    public void setSolutionDate(String solutionDate) {
        this.solutionDate = solutionDate;
    }

    public String getLanguageShortName() {
        return languageShortName;
    }

    public void setLanguageShortName(String languageShortName) {
        this.languageShortName = languageShortName;
    }

}
