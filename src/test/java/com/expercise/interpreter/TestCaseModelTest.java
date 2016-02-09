package com.expercise.interpreter;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.TestCase;
import com.expercise.enums.DataType;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.ChallengeInputTypeBuilder;
import com.expercise.testutils.builder.TestCaseBuilder;
import com.expercise.testutils.builder.TestCaseInputValueBuilder;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestCaseModelTest {

    @Test
    public void shouldCreateTestCaseModel() {
        Challenge challenge = new ChallengeBuilder().outputType(DataType.Text).buildWithRandomId();
        new ChallengeInputTypeBuilder().challenge(challenge).inputType(DataType.Text).buildWithRandomId();
        new ChallengeInputTypeBuilder().challenge(challenge).inputType(DataType.Integer).buildWithRandomId();

        TestCase testCase = new TestCaseBuilder().challenge(challenge)
                .inputs(new TestCaseInputValueBuilder().inputValue("40").buildWithRandomId(),
                        new TestCaseInputValueBuilder().inputValue("50").buildWithRandomId())
                .output("20")
                .challenge(challenge)
                .buildWithRandomId();

        TestCaseWithResult testCaseWithResult = new TestCaseWithResult(testCase);
        testCaseWithResult.setActualValue("10");
        testCaseWithResult.setTestCaseResult(TestCaseResult.FAILED);
        testCaseWithResult.setResultMessage("result message");

        TestCaseModel createdModel = TestCaseModel.createFrom(testCaseWithResult);

        assertThat(createdModel.getInputs(), hasItems("\"40\"", "50"));
        assertThat(createdModel.getActualValue(), equalTo("\"10\""));
        assertThat(createdModel.getOutput(), equalTo("\"20\""));
        assertThat(createdModel.getTestCaseResult(), equalTo(TestCaseResult.FAILED));
        assertThat(createdModel.getResultMessage(), equalTo("result message"));
    }

}