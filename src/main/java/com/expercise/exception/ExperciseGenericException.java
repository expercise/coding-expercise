package com.expercise.exception;

public class ExperciseGenericException extends RuntimeException {

    public ExperciseGenericException(String message, Throwable exception) {
        super(message, exception);
    }

}