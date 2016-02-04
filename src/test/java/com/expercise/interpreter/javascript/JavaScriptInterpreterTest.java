package com.expercise.interpreter.javascript;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.ChallengeInputType;
import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.challenge.TestCaseInputValue;
import com.expercise.enums.DataType;
import com.expercise.interpreter.InterpreterFailureType;
import com.expercise.interpreter.InterpreterResult;
import com.expercise.interpreter.TestCaseResult;
import com.expercise.interpreter.TestCaseWithResult;
import com.expercise.interpreter.typechecker.TypeChecker;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JavaScriptInterpreterTest {

    @InjectMocks
    private JavaScriptInterpreter interpreter;

    @Mock
    private TypeChecker typeChecker;

    @Before
    public void init() {
        when(typeChecker.typeCheck(any(), any(DataType.class))).thenReturn(true);
    }

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
        TestCaseWithResult successfulTestCase = new TestCaseWithResult(testCase);
        successfulTestCase.setActualValue("35");
        successfulTestCase.setTestCaseResult(TestCaseResult.PASSED);
        context.addTestCaseWithResult(successfulTestCase);

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

        ChallengeEvaluationContext context = createContext(challenge, sumSolution);

        interpreter.interpret(context);

        InterpreterResult interpreterResult = context.getInterpreterResult();
        assertFalse(interpreterResult.isSuccess());
        assertThat(interpreterResult.getFailureType(), nullValue());

        List<TestCaseWithResult> testCaseWithResults = context.getTestCaseWithResults();
        assertThat(testCaseWithResults.size(), equalTo(1));
        TestCaseWithResult firstTestCaseWithResult = testCaseWithResults.get(0);
        assertThat(firstTestCaseWithResult.getExpectedValue(), equalTo("35"));
        assertThat(firstTestCaseWithResult.getActualValue(), equalTo("12"));
        assertFalse(interpreterResult.isSuccess());
    }

    @Test
    public void shouldFailForBothTwoTestCases() {
        String sumSolution = "function solution(a, b) { return a; }";

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);
        inputTypes.add(DataType.Integer);

        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Integer);

        TestCase testCase1 = new TestCase();
        List<String> inputValues1 = new ArrayList<>();
        inputValues1.add("12");
        inputValues1.add("23");
        testCase1.setInputs(TestCaseInputValue.createFrom(inputValues1));
        testCase1.setOutput("35");

        TestCase testCase2 = new TestCase();
        List<String> inputValues2 = new ArrayList<>();
        inputValues2.add("11");
        inputValues2.add("21");
        testCase2.setInputs(TestCaseInputValue.createFrom(inputValues2));
        testCase2.setOutput("32");

        challenge.addTestCase(testCase1);
        challenge.addTestCase(testCase2);

        ChallengeEvaluationContext context = createContext(challenge, sumSolution);

        interpreter.interpret(context);

        InterpreterResult interpreterResult = context.getInterpreterResult();
        assertFalse(interpreterResult.isSuccess());
        assertThat(interpreterResult.getFailureType(), nullValue());

        List<TestCaseWithResult> testCaseWithResults = context.getTestCaseWithResults();
        assertThat(testCaseWithResults.size(), equalTo(2));
        TestCaseWithResult firstTestCaseWithResult = testCaseWithResults.get(0);
        assertThat(firstTestCaseWithResult.getExpectedValue(), equalTo("35"));
        assertThat(firstTestCaseWithResult.getActualValue(), equalTo("12"));

        TestCaseWithResult secondTestCaseWithResult = testCaseWithResults.get(1);
        assertThat(secondTestCaseWithResult.getExpectedValue(), equalTo("32"));
        assertThat(secondTestCaseWithResult.getActualValue(), equalTo("11"));

        assertFalse(interpreterResult.isSuccess());
    }

    @Test
    public void shouldReturnFailedResultIfSolutionHasSyntaxError() {
        String syntaxErrorMessage =
                "solution.js:1:10 Missing space after numeric literal\n" +
                "function 5solution(a) {}\n" +
                "          ^ in solution.js at line number 1 at column number 10";

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);

        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();

        List<String> inputValues = new ArrayList<>();
        inputValues.add("12");
        testCase.setInputs(TestCaseInputValue.createFrom(inputValues));
        testCase.setOutput("35");

        challenge.addTestCase(testCase);

        ChallengeEvaluationContext context = createContext(challenge, "function 5solution(a) {}");

        interpreter.interpret(context);

        assertFalse(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getConsoleMessage(), equalTo(syntaxErrorMessage));
        assertThat(context.getInterpreterResult().getFailureType(), equalTo(InterpreterFailureType.SYNTAX_ERROR));
    }

    @Test
    public void shouldReturnFailedResultIfSolutionHasReferenceError() {
        String syntaxErrorMessage = "ReferenceError: \"abc\" is not defined in solution.js at line number 2";

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);

        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();

        List<String> inputValues = new ArrayList<>();
        inputValues.add("12");
        testCase.setInputs(TestCaseInputValue.createFrom(inputValues));
        testCase.setOutput("35");

        challenge.addTestCase(testCase);

        ChallengeEvaluationContext context = createContext(challenge, "function solution(a) { \nreturn abc;\n }");

        interpreter.interpret(context);

        assertFalse(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getConsoleMessage(), equalTo(syntaxErrorMessage));
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
    public void shouldReturnFailedResultIfOutputTypeAndResultValueTypeHasTypeConflictError() {
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

        ChallengeEvaluationContext context = createContext(challenge, "function solution(a, b) { return \"49\" }");

        when(typeChecker.typeCheck(any(), eq(DataType.Integer))).thenReturn(false);

        interpreter.interpret(context);

        assertThat(context.getInterpreterResult().getFailureType(), equalTo(InterpreterFailureType.TYPE_ERROR));
        assertFalse(context.getInterpreterResult().isSuccess());
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
        for (TestCase testCase : challenge.getTestCases()) {
            context.addTestCaseWithResult(new TestCaseWithResult(testCase));
        }
        return context;
    }

}
