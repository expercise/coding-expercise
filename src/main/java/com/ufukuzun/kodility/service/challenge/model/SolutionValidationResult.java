package com.ufukuzun.kodility.service.challenge.model;

public class SolutionValidationResult {

    private boolean success;

    public SolutionValidationResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
