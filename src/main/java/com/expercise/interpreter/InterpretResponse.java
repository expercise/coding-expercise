package com.expercise.interpreter;

public class InterpretResponse {

    private String stdOut;
    private String stdErr;

    public String getStdOut() {
        return stdOut;
    }

    public void setStdOut(String stdOut) {
        this.stdOut = stdOut;
    }

    public String getStdErr() {
        return stdErr;
    }

    public void setStdErr(String stdErr) {
        this.stdErr = stdErr;
    }

    @Override
    public String toString() {
        return "InterpretResponse{" +
                "stdOut='" + stdOut + '\'' +
                ", stdErr='" + stdErr + '\'' +
                '}';
    }
}
