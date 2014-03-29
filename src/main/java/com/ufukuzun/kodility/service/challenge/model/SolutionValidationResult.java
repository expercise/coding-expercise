package com.ufukuzun.kodility.service.challenge.model;

public class SolutionValidationResult {

    private String result;

    private boolean success;

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

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
