package com.ufukuzun.kodility.interpreter;

public class InterpreterResult {

    private String result;

    private boolean success;

    private InterpreterResult(String result, boolean success) {
        this.result = result;
        this.success = success;
    }

    public static InterpreterResult createSuccessResult(String result) {
        return new InterpreterResult(result, true);
    }

    public static InterpreterResult createFailedResult(String result) {
        return new InterpreterResult(result, false);
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
