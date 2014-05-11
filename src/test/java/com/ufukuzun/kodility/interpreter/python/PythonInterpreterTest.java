package com.ufukuzun.kodility.interpreter.python;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.i18n.MessageService;
import com.ufukuzun.kodility.testutils.InterpreterTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PythonInterpreterTest {

    @InjectMocks
    private PythonInterpreter interpreter;

    @Mock
    private MessageService messageService;

    @Test
    public void shouldSendErrorMessageWhenThereIsSyntaxError() {
        Challenge challenge = new Challenge();

        when(messageService.getMessage("interpreter.syntaxError")).thenReturn("Syntax Error");

        InterpreterResult result = interpreter.interpret("def foo(a, b): Keturn a -+ b;", challenge);

        assertFalse(result.isSuccess());
        assertThat(result.getResult(), equalTo("Syntax Error"));
    }

    @Test
    public void shouldEvaluatePythonCodeWithSingleTestCaseForSuccessfulCase() {
        String sumSolution = InterpreterTestUtils.getSourceFrom("/pythonTestSourceCodes/simplePlus.py");

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
    public void shouldEvaluateMultipleTestCaseWithPythonSourceCodeForSuccessfulCase() {
        String sumSolution = InterpreterTestUtils.getSourceFrom("/pythonTestSourceCodes/simplePlus.py");

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);
        inputTypes.add(DataType.Integer);

        challenge.setInputTypes(inputTypes);
        challenge.setOutputType(DataType.Integer);

        TestCase testCase1 = new TestCase();

        List<Object> inputValues1 = new ArrayList<>();
        inputValues1.add(12);
        inputValues1.add(23);
        testCase1.setInputs(inputValues1);
        testCase1.setOutput(35);

        TestCase testCase2 = new TestCase();

        List<Object> inputValues2 = new ArrayList<>();
        inputValues2.add(120);
        inputValues2.add(23);
        testCase2.setInputs(inputValues2);
        testCase2.setOutput(143);

        challenge.addTestCase(testCase1);
        challenge.addTestCase(testCase2);

        InterpreterResult interpreterResult = interpreter.interpret(sumSolution, challenge);

        assertTrue(interpreterResult.isSuccess());
    }

    @Test
    public void shouldEvaluateCodeForConcatenationCodeOfStringInputs() {
        String concatSolution = InterpreterTestUtils.getSourceFrom("/pythonTestSourceCodes/simplePlus.py");

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Text);
        inputTypes.add(DataType.Text);

        challenge.setInputTypes(inputTypes);
        challenge.setOutputType(DataType.Text);

        TestCase testCase = new TestCase();

        List<Object> inputValues = new ArrayList<>();
        inputValues.add("ahmet");
        inputValues.add("mehmet");
        testCase.setInputs(inputValues);
        testCase.setOutput("ahmetmehmet");

        challenge.addTestCase(testCase);

        InterpreterResult interpreterResult = interpreter.interpret(concatSolution, challenge);

        assertTrue(interpreterResult.isSuccess());
    }

    @Test
    public void shouldReturnExceptionMessageIfEvaluationCauseAnException() {
        String concatSolution = "def solution(a, b): return a+BB";

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Text);
        inputTypes.add(DataType.Text);

        challenge.setInputTypes(inputTypes);
        challenge.setOutputType(DataType.Text);

        TestCase testCase = new TestCase();

        List<Object> inputValues = new ArrayList<>();
        inputValues.add("ahmet");
        inputValues.add("mehmet");
        testCase.setInputs(inputValues);
        testCase.setOutput("ahmetmehmet");

        challenge.addTestCase(testCase);

        InterpreterResult interpreterResult = interpreter.interpret(concatSolution, challenge);

        assertFalse(interpreterResult.isSuccess());
        assertThat(interpreterResult.getResult(), equalTo("global name 'BB' is not defined"));
    }

    @Test
    public void shouldWorkWithTextParameters() {
        String solution = "def solution(a): return len(a)";

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

        InterpreterResult interpreterResult = interpreter.interpret(solution, challenge);

        assertTrue(interpreterResult.isSuccess());
    }

    @Test
    public void shouldReturnTextValue() {
        String solution = "def solution(): return \"Hello World\"";

        Challenge challenge = new Challenge();

        challenge.setOutputType(DataType.Text);

        TestCase testCase = new TestCase();
        testCase.setOutput("Hello World");

        challenge.addTestCase(testCase);

        InterpreterResult interpreterResult = interpreter.interpret(solution, challenge);

        assertTrue(interpreterResult.isSuccess());
    }

}