package com.expercise.exception;

import java.io.IOException;

public class ExperciseJsonException extends IOException {

    private static final long serialVersionUID = 5314405037914650141L;

    public ExperciseJsonException(String message, Throwable exception) {
        super(message, exception);
    }

}