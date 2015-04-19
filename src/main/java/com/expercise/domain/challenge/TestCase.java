package com.expercise.domain.challenge;

import com.expercise.domain.PrioritizedEntity;
import com.expercise.enums.DataType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TestCase extends PrioritizedEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;

    @OneToMany(mappedBy = "testCase", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("priority")
    private List<TestCaseInputValue> inputs = new ArrayList<>();

    @Column(nullable = false)
    private String output;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
