package com.expercise.interpreter;

public class InterpreterResult {

    private InterpreterFailureType failureType;

    private String consoleMessage;

    private boolean success;

    private InterpreterResult(boolean success) {
        this.success = success;
    }

    public static InterpreterResult createSuccessResult() {
        return new InterpreterResult(true);
    }

    public static InterpreterResult createFailedResult() {
        return new InterpreterResult(false);
    }

    public static InterpreterResult noResultFailedResult() {
        InterpreterResult failedResult = createFailedResult();
        failedResult.setFailureType(InterpreterFailureType.NO_RESULT);
        return failedResult;
    }

    public static InterpreterResult syntaxErrorFailedResult(String errorMessage) {
        InterpreterResult failedResult = createFailedResult();
        failedResult.setFailureType(InterpreterFailureType.SYNTAX_ERROR);
        failedResult.setConsoleMessage(errorMessage);
        return failedResult;
    }

    public static InterpreterResult typeErrorFailedResult() {
        InterpreterResult failedResult = createFailedResult();
        failedResult.setFailureType(InterpreterFailureType.TYPE_ERROR);
        return failedResult;
    }

    public InterpreterFailureType getFailureType() {
        return failureType;
    }

    public void setFailureType(InterpreterFailureType failureType) {
        this.failureType = failureType;
    }

    public String getConsoleMessage() {
        return consoleMessage;
    }

    public void setConsoleMessage(String consoleMessage) {
        this.consoleMessage = consoleMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}