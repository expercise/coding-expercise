package com.ufukuzun.kodility.controller.challenge.model;

import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.utils.DateUtils;

import java.io.Serializable;

public class UserSolutionModel implements Serializable, Comparable<UserSolutionModel> {

    private String programmingLanguage;
    private String solution;
    private String solutionDate;

    private UserSolutionModel() {}

    public static UserSolutionModel createFrom(Solution solution) {
        UserSolutionModel userSolutionModel = new UserSolutionModel();
        userSolutionModel.setProgrammingLanguage(solution.getProgrammingLanguage().name());
        userSolutionModel.setSolution(solution.getSolution());
        userSolutionModel.setSolutionDate(DateUtils.formatDateToLongFormat(solution.getCreateDate()));

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

    @Override
    public int compareTo(UserSolutionModel other) {
        return DateUtils.longFormatToDate(other.getSolutionDate()).compareTo(DateUtils.longFormatToDate(this.getSolutionDate()));
    }

}
