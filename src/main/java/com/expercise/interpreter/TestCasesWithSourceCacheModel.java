package com.expercise.interpreter;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestCasesWithSourceCacheModel implements Serializable {

    private static final long serialVersionUID = -423521965916958053L;

    private String currentSourceCode = StringUtils.EMPTY;

    private List<TestCaseWithResult> testCaseResults = new ArrayList<>();

    public TestCasesWithSourceCacheModel(List<TestCaseWithResult> testCaseResults) {
        this(StringUtils.EMPTY, testCaseResults);
    }

    public TestCasesWithSourceCacheModel(String currentSourceCode, List<TestCaseWithResult> testCaseResults) {
        this.currentSourceCode = currentSourceCode;
        this.testCaseResults = testCaseResults;
    }

    public void clear() {
        this.currentSourceCode = StringUtils.EMPTY;
        this.testCaseResults.clear();
    }

    public void reset() {
        this.currentSourceCode = StringUtils.EMPTY;
        this.testCaseResults.stream().forEach(t -> {
            t.setTestCaseResult(TestCaseResult.NEW);
            t.setActualValue(null);
            t.setResultMessage(StringUtils.EMPTY);
        });
    }

    public String getCurrentSourceCode() {
        return currentSourceCode;
    }

    public void setCurrentSourceCode(String currentSourceCode) {
        this.currentSourceCode = currentSourceCode;
    }

    public List<TestCaseWithResult> getTestCaseResults() {
        return testCaseResults;
    }

    public void setTestCaseResults(List<TestCaseWithResult> testCaseResults) {
        this.testCaseResults = testCaseResults;
    }

}
