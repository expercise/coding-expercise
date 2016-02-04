package com.expercise.interpreter;

public class InterpreterException extends Exception {

    private static final long serialVersionUID = -9010793998200028098L;

    private final InterpreterResult interpreterResult;

    public InterpreterException(InterpreterResult interpreterResult) {
        this.interpreterResult = interpreterResult;
    }

    public InterpreterResult getInterpreterResult() {
        return interpreterResult;
    }

}
