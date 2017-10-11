package com.expercise.testutils.builder;

import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.challenge.TestCaseInputValue;

public class TestCaseInputValueBuilder extends BasePrioritizedEntityBuilder<TestCaseInputValue, TestCaseInputValueBuilder> {

    private String inputValue;

    private TestCase testCase;

    @Override
    protected TestCaseInputValue getInstance() {
        TestCaseInputValue testCaseInputValue = new TestCaseInputValue();
        testCaseInputValue.setInputValue(inputValue);
        testCaseInputValue.setTestCase(testCase);
        return testCaseInputValue;
    }

    public TestCaseInputValueBuilder inputValue(String inputValue) {
        this.inputValue = inputValue;
        return this;
    }

    public TestCaseInputValueBuilder testCase(TestCase testCase) {
        this.testCase = testCase;
        return this;
    }

}
