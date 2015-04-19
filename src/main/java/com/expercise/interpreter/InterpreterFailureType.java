package com.expercise.interpreter;

public enum InterpreterFailureType {

    NO_RESULT("interpreter.noResult"),
    SYNTAX_ERROR("interpreter.syntaxError"),
    TYPE_ERROR("interpreter.typeError");

    private String messageKey;

    InterpreterFailureType(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

}
