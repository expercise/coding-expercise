package com.expercise.interpreter;

public class InterpretRequest {

    private final String sourceCode;
    private final String programmingLanguage;

    public InterpretRequest(String sourceCode, String programmingLanguage) {
        this.sourceCode = sourceCode;
        this.programmingLanguage = programmingLanguage;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }
}
