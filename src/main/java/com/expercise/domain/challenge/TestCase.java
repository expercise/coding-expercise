package com.expercise.domain.challenge;

import com.expercise.domain.PrioritizedEntity;
import com.expercise.enums.DataType;
import com.expercise.utils.Constants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "SEQ_TEST_CASE")
public class TestCase extends PrioritizedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;

    @OneToMany(mappedBy = "testCase", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("priority")
    private List<TestCaseInputValue> inputs = new ArrayList<>();

    @Column(nullable = false, length = Constants.MAX_TESTCASE_VALUE_LENGTH)
    private String output;

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public List<TestCaseInputValue> getInputs() {
        return inputs;
    }

    public void setInputs(List<TestCaseInputValue> inputs) {
        inputs.forEach(i -> i.setTestCase(this));
        this.inputs = inputs;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public DataType getOutputType() {
        return getChallenge().getOutputType();
    }

}
