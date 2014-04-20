package com.ufukuzun.kodility.domain.challenge;

import java.util.ArrayList;
import java.util.List;

public class TestCase {

    private List<Object> inputs = new ArrayList<>();

    private Object output;

    public List<Object> getInputs() {
        return inputs;
    }

    public void setInputs(List<Object> inputs) {
        this.inputs = inputs;
    }

    public Object getOutput() {
        return output;
    }

    public void setOutput(Object output) {
        this.output = output;
    }
}
