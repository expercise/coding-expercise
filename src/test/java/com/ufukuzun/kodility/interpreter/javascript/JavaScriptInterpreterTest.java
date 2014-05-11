package com.ufukuzun.kodility.interpreter.javascript;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.i18n.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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

        challenge.setInputTypes(inputTypes);
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();

        List<Object> inputValues = new ArrayList<>();
        inputValues.add(12);
        inputValues.add(23);
        testCase.setInputs(inputValues);
        testCase.setOutput(35);

        challenge.addTestCase(testCase);

        InterpreterResult interpreterResult = interpreter.interpret(sumSolution, challenge);

        assertTrue(interpreterResult.isSuccess());
    }

    @Test
    public void shouldFailedResultIfTestCasesNotPassed() {
        String sumSolution = "function solution(a, b) { return a; }";

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);
        inputTypes.add(DataType.Integer);

        challenge.setInputTypes(inputTypes);
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();

        List<Object> inputValues = new ArrayList<>();
        inputValues.add(12);
        inputValues.add(23);
        testCase.setInputs(inputValues);
        testCase.setOutput(35);

        challenge.addTestCase(testCase);

        InterpreterResult interpreterResult = interpreter.interpret(sumSolution, challenge);

        assertFalse(interpreterResult.isSuccess());
    }

    @Test
    public void shouldReturnExceptionMessageIfEvaluationCauseAnException() {
        InterpreterResult result = interpreter.interpret("fuNksin fuu(){}", new Challenge());

        assertFalse(result.isSuccess());
        assertThat(result.getResult(), equalTo("missing ; before statement (solution.js#1) in solution.js at line number 1"));
    }

    @Test
    public void shouldReturnFailedResultIfSolutionHasNoResult() {
        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);
        inputTypes.add(DataType.Integer);

        challenge.setInputTypes(inputTypes);
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();

        List<Object> inputValues = new ArrayList<>();
        inputValues.add(12);
        inputValues.add(23);
        testCase.setInputs(inputValues);
        testCase.setOutput(35);

        challenge.addTestCase(testCase);

        InterpreterResult result = interpreter.interpret("function solution(a, b) {}", challenge);

        assertFalse(result.isSuccess());
    }

    @Test
    public void shouldWorkWithTextParameters() {
        String sumsolution = "function solution(a) { return a.length; }";

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Text);

        challenge.setInputTypes(inputTypes);
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();

        List<Object> inputValues = new ArrayList<>();
        inputValues.add("abc");
        testCase.setInputs(inputValues);
        testCase.setOutput(3);

        challenge.addTestCase(testCase);

        InterpreterResult interpreterResult = interpreter.interpret(sumsolution, challenge);

        assertTrue(interpreterResult.isSuccess());
    }

    @Test
    public void shouldReturnTextValue() {
        String solution = "function solution(a) { return 'Hello World' }";

        Challenge challenge = new Challenge();

        challenge.setOutputType(DataType.Text);

        TestCase testCase = new TestCase();
        testCase.setOutput("Hello World");

        challenge.addTestCase(testCase);

        InterpreterResult interpreterResult = interpreter.interpret(solution, challenge);

        assertTrue(interpreterResult.isSuccess());
    }

}
