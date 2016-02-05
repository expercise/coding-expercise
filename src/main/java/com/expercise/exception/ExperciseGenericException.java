package com.expercise.exception;

public class ExperciseGenericException extends RuntimeException {

    private static final long serialVersionUID = -4059537275690891978L;

    public ExperciseGenericException(String message, Throwable exception) {
        super(message, exception);
    }

}