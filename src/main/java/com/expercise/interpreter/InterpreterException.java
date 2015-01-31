package com.expercise.interpreter;

public class InterpreterException extends Exception {

    private final InterpreterResult interpreterResult;

    public InterpreterException(InterpreterResult interpreterResult) {
        this.interpreterResult = interpreterResult;
    }

    public InterpreterResult getInterpreterResult() {
        return interpreterResult;
    }

}
