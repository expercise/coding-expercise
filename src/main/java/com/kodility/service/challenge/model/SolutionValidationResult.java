package com.kodility.service.challenge.model;

import com.kodility.controller.challenge.model.UserSolutionModel;

import java.util.ArrayList;

public class SolutionValidationResult {

    private String result;

    private boolean success;

    private ArrayList<UserSolutionModel> userSolutionModels;

    private SolutionValidationResult(String result, boolean success) {
        this.result = result;
        this.success = success;
    }

    public static SolutionValidationResult createSuccessResult(String result) {
        return new SolutionValidationResult(result, true);
    }

    public static SolutionValidationResult createFailedResult(String result) {
        return new SolutionValidationResult(result, false);
    }

    public String getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<UserSolutionModel> getUserSolutionModels() {
        return userSolutionModels;
    }

    public void setUserSolutionModels(ArrayList<UserSolutionModel> userSolutionModels) {
        this.userSolutionModels = userSolutionModels;
    }

    public void addErrorDescriptionToResult(String errorDescription) {
        this.result = errorDescription + ". " + this.result;
    }

}
