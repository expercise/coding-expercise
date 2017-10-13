package com.expercise.domain.challenge;

import com.expercise.domain.PrioritizedEntity;
import com.expercise.utils.Constants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "SEQ_TEST_CASE_INPUT_VALUE")
public class TestCaseInputValue extends PrioritizedEntity {

    @Column(nullable = false, length = Constants.MAX_TESTCASE_VALUE_LENGTH)
    private String inputValue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TestCase testCase;

    public static List<TestCaseInputValue> createFrom(List<String> inputValues) {
        List<TestCaseInputValue> testCaseInputValues = new ArrayList<>();
        for (String inputValue : inputValues) {
            TestCaseInputValue testCaseInputValue = new TestCaseInputValue();
            testCaseInputValue.setInputValue(inputValue);
            testCaseInputValues.add(testCaseInputValue);
        }
        prioritize(testCaseInputValues);
        return testCaseInputValues;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

}
