package com.ufukuzun.kodility.exception;

public class KodilityGenericException extends RuntimeException {

    public KodilityGenericException(String message, Throwable exception) {
        super(message, exception);
    }

}