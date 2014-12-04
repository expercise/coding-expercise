package com.kodility.interpreter.javascript;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.ChallengeInputType;
import com.kodility.domain.challenge.TestCase;
import com.kodility.domain.challenge.TestCaseInputValue;
import com.kodility.enums.DataType;
import com.kodility.interpreter.InterpreterFailureType;
import com.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JavaScriptInterpreterTest {

    @InjectMocks
    private JavaScriptInterpreter interpreter;

    @Test
    public void shouldEvaluateSolutionWithTestCases() {
        String sumSolution = "function solution(a, b) { return a+b; }";

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);
        inputTypes.add(DataType.Integer);

        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();

        List<String> inputValues = new ArrayList<>();
        inputValues.add("12");
        inputValues.add("23");
        testCase.setInputs(TestCaseInputValue.createFrom(inputValues));
        testCase.setOutput("35");

        challenge.addTestCase(testCase);

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(challenge);
        context.setSource(sumSolution);

        interpreter.interpret(context);

        assertTrue(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), nullValue());
    }

    @Test
    public void shouldFailedResultIfTestCasesNotPassed() {
        String sumSolution = "function solution(a, b) { return a; }";

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);
        inputTypes.add(DataType.Integer);

        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();

        List<String> inputValues = new ArrayList<>();
        inputValues.add("12");
        inputValues.add("23");
        testCase.setInputs(TestCaseInputValue.createFrom(inputValues));
        testCase.setOutput("35");

        challenge.addTestCase(testCase);

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(challenge);
        context.setSource(sumSolution);

        interpreter.interpret(context);

        assertFalse(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), nullValue());
    }

    @Test
    public void shouldReturnFailedResultIfSolutionHasNoResult() {
        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);
        inputTypes.add(DataType.Integer);

        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();

        List<String> inputValues = new ArrayList<>();
        inputValues.add("12");
        inputValues.add("23");
        testCase.setInputs(TestCaseInputValue.createFrom(inputValues));
        testCase.setOutput("35");

        challenge.addTestCase(testCase);

        ChallengeEvaluationContext context = createContext(challenge, "function solution(a, b) {}");

        interpreter.interpret(context);

        assertFalse(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), equalTo(InterpreterFailureType.NO_RESULT));
    }

    @Test
    public void shouldWorkWithTextParameters() {
        String sumSolution = "function solution(a) { return a.length; }";

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Text);

        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();

        List<String> inputValues = new ArrayList<>();
        inputValues.add("abc");
        testCase.setInputs(TestCaseInputValue.createFrom(inputValues));
        testCase.setOutput("3");

        challenge.addTestCase(testCase);

        ChallengeEvaluationContext context = createContext(challenge, sumSolution);

        interpreter.interpret(context);

        assertTrue(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), nullValue());
    }

    @Test
    public void shouldReturnTextValue() {
        String solution = "function solution(a) { return 'Hello World' }";

        Challenge challenge = new Challenge();

        challenge.setOutputType(DataType.Text);

        TestCase testCase = new TestCase();
        testCase.setOutput("Hello World");

        challenge.addTestCase(testCase);

        ChallengeEvaluationContext context = createContext(challenge, solution);

        interpreter.interpret(context);

        assertTrue(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), nullValue());
    }

    @Test
    public void shouldNotAllowJavaUsageInJavaScript() {
        String solution = "function solution(a) { return Java.type(\"java.lang.Math\").pow(a, 2); }";

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);

        Challenge challenge = new Challenge();
        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();
        List<String> inputValues = new ArrayList<>();
        inputValues.add("2");
        testCase.setInputs(TestCaseInputValue.createFrom(inputValues));
        testCase.setOutput("4");

        challenge.addTestCase(testCase);

        ChallengeEvaluationContext context = createContext(challenge, solution);

        interpreter.interpret(context);

        assertFalse(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), nullValue());
    }

    private ChallengeEvaluationContext createContext(Challenge challenge, String source) {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(challenge);
        context.setSource(source);
        return context;
    }

}
