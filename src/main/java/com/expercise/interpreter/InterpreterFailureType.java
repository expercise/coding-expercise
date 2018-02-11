package com.expercise.interpreter;

public enum InterpreterFailureType {

    NO_RESULT("interpreter.noResult");

    private String messageKey;

    InterpreterFailureType(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
