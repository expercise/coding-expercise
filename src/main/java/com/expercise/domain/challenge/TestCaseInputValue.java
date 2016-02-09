package com.expercise.domain.challenge;

import com.expercise.domain.PrioritizedEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TestCaseInputValue extends PrioritizedEntity {

    private static final long serialVersionUID = 5844311948411636275L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String inputValue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TestCase testCase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


}
