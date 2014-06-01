package com.ufukuzun.kodility.interpreter.javascript;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.ChallengeInputType;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.domain.challenge.TestCaseInputValue;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.i18n.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JavaScriptInterpreterTest {

    @InjectMocks
    private JavaScriptInterpreter interpreter;

    @Mock
    private MessageService messageService;

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
    }

    @Test
    public void shouldReturnExceptionMessageIfEvaluationCauseAnException() {
        ChallengeEvaluationContext context = createContext(new Challenge(), "fuNksin fuu(){}");
        interpreter.interpret(context);

        assertFalse(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getResult(), equalTo("missing ; before statement (solution.js#1) in solution.js at line number 1"));
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
    }

    @Test
    public void shouldWorkWithTextParameters() {
        String sumsolution = "function solution(a) { return a.length; }";

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

        ChallengeEvaluationContext context = createContext(challenge, sumsolution);

        interpreter.interpret(context);

        assertTrue(context.getInterpreterResult().isSuccess());
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
    }

    private ChallengeEvaluationContext createContext(Challenge challenge, String source) {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(challenge);
        context.setSource(source);
        return context;
    }

}
