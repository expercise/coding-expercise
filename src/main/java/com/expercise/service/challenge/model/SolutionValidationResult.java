package com.expercise.service.challenge.model;

import com.expercise.controller.challenge.model.UserSolutionModel;
import com.expercise.interpreter.TestCaseModel;
import com.expercise.interpreter.TestCasesWithSourceModel;

import java.util.List;

public class SolutionValidationResult {

    private String result;

    private String consoleMessage;

    private boolean success;

    private ChallengeSolutionStatus challengeSolutionStatus;

    private TestCasesWithSourceModel testCasesWithSourceModel = new TestCasesWithSourceModel();

    private List<UserSolutionModel> userSolutionModels;

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

    public List<UserSolutionModel> getUserSolutionModels() {
        return userSolutionModels;
    }

    public void setUserSolutionModels(List<UserSolutionModel> userSolutionModels) {
        this.userSolutionModels = userSolutionModels;
    }

    public ChallengeSolutionStatus getChallengeSolutionStatus() {
        return challengeSolutionStatus;
    }

    public void setChallengeSolutionStatus(ChallengeSolutionStatus challengeSolutionStatus) {
        this.challengeSolutionStatus = challengeSolutionStatus;
    }

    public TestCasesWithSourceModel getTestCasesWithSourceModel() {
        return testCasesWithSourceModel;
    }

    public String getConsoleMessage() {
        return consoleMessage;
    }

    public void setConsoleMessage(String consoleMessage) {
        this.consoleMessage = consoleMessage;
    }

    public void addErrorDescriptionToResult(String errorDescription) {
        this.result = errorDescription + ". " + this.result;
    }

    public void addTestCaseModel(TestCaseModel testCaseModel) {
        getTestCasesWithSourceModel().getTestCaseModels().add(testCaseModel);
    }

}
