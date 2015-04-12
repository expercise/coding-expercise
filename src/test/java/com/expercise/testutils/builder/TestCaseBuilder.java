package com.expercise.testutils.builder;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.challenge.TestCaseInputValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestCaseBuilder extends AbstractPrioritizedEntityBuilder<TestCase, TestCaseBuilder> {

    private Challenge challenge;

    private List<TestCaseInputValue> inputs = new ArrayList<>();

    private String output;

    @Override
    protected TestCase getInstance() {
        TestCase testCase = new TestCase();
        challenge.addTestCase(testCase);
        inputs.stream().forEach(input -> {
            testCase.getInputs().add(input);
            input.setTestCase(testCase);
        });
        testCase.setOutput(output);
        return testCase;
    }

    public TestCaseBuilder challenge(Challenge challenge) {
        this.challenge = challenge;
        return this;
    }

    public TestCaseBuilder inputs(TestCaseInputValue... inputsValues) {
        Collections.addAll(inputs, inputsValues);
        return this;
    }

    public TestCaseBuilder output(String output) {
        this.output = output;
        return this;
    }

}
