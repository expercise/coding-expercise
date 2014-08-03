package com.ufukuzun.kodility.interpreter;

public class InterpreterResult {

    private String result;

    private boolean success;

    public InterpreterResult() {}

    private InterpreterResult(boolean success) {
        this.success = success;
    }

    public static InterpreterResult createSuccessResult() {
        return new InterpreterResult(true);
    }

    public static InterpreterResult createFailedResult() {
        return new InterpreterResult(false);
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