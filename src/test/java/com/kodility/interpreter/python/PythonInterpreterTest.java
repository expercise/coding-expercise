package com.kodility.interpreter.python;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.ChallengeInputType;
import com.kodility.domain.challenge.TestCase;
import com.kodility.domain.challenge.TestCaseInputValue;
import com.kodility.enums.DataType;
import com.kodility.interpreter.InterpreterFailureType;
import com.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.kodility.testutils.FileTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PythonInterpreterTest {

    @InjectMocks
    private PythonInterpreter interpreter;

    @Test
    public void shouldSendErrorMessageWhenThereIsSyntaxError() {
        Challenge challenge = new Challenge();

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setSource("def foo(a, b): Keturn a -+ b;");
        context.setChallenge(challenge);

        interpreter.interpret(context);

        assertFalse(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), equalTo(InterpreterFailureType.SYNTAX_ERROR));
    }

    @Test
    public void shouldEvaluatePythonCodeWithSingleTestCaseForSuccessfulCase() {
        String sumSolution = FileTestUtils.getFileContentFrom("/pythonTestSourceCodes/simplePlus.py");

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

        assertTrue(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), nullValue());
    }

    @Test
    public void shouldEvaluatePythonCodeFromImportedModule() {
        String sumSolution = FileTestUtils.getFileContentFrom("/pythonTestSourceCodes/simpleImport.py");

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);

        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();

        List<String> inputValues = new ArrayList<>();
        inputValues.add("11");
        testCase.setInputs(TestCaseInputValue.createFrom(inputValues));
        testCase.setOutput("121");

        challenge.addTestCase(testCase);

        ChallengeEvaluationContext context = createContext(challenge, sumSolution);

        interpreter.interpret(context);

        assertTrue(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), nullValue());
    }

    @Test
    public void shouldEvaluateMultipleTestCaseWithPythonSourceCodeForSuccessfulCase() {
        String sumSolution = FileTestUtils.getFileContentFrom("/pythonTestSourceCodes/simplePlus.py");

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
        inputValues2.add("120");
        inputValues2.add("23");
        testCase2.setInputs(TestCaseInputValue.createFrom(inputValues2));
        testCase2.setOutput("143");

        challenge.addTestCase(testCase1);
        challenge.addTestCase(testCase2);

        ChallengeEvaluationContext context = createContext(challenge, sumSolution);

        interpreter.interpret(context);

        assertTrue(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), nullValue());
    }

    @Test
    public void shouldEvaluateCodeForConcatenationCodeOfStringInputs() {
        String concatSolution = FileTestUtils.getFileContentFrom("/pythonTestSourceCodes/simplePlus.py");

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Text);
        inputTypes.add(DataType.Text);

        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Text);

        TestCase testCase = new TestCase();

        List<String> inputValues = new ArrayList<>();
        inputValues.add("ahmet");
        inputValues.add("mehmet");
        testCase.setInputs(TestCaseInputValue.createFrom(inputValues));
        testCase.setOutput("ahmetmehmet");

        challenge.addTestCase(testCase);

        ChallengeEvaluationContext context = createContext(challenge, concatSolution);

        interpreter.interpret(context);

        assertTrue(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), nullValue());
    }

    @Test
    public void shouldReturnExceptionMessageIfEvaluationCauseAnException() {
        String concatSolution = "def solution(a, b): return a+BB";

        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Text);
        inputTypes.add(DataType.Text);

        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Text);

        TestCase testCase = new TestCase();

        List<String> inputValues = new ArrayList<>();
        inputValues.add("ahmet");
        inputValues.add("mehmet");
        testCase.setInputs(TestCaseInputValue.createFrom(inputValues));
        testCase.setOutput("ahmetmehmet");

        challenge.addTestCase(testCase);

        ChallengeEvaluationContext context = createContext(challenge, concatSolution);

        interpreter.interpret(context);

        assertFalse(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), nullValue());
    }

    @Test
    public void shouldWorkWithTextParameters() {
        String solution = "def solution(a): return len(a)";

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

        ChallengeEvaluationContext context = createContext(challenge, solution);

        interpreter.interpret(context);

        assertTrue(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), nullValue());
    }

    @Test
    public void shouldReturnTextValue() {
        String solution = "def solution(): return \"Hello World\"";

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
    public void shouldFailedIfReturnValueTypeAndOutputTypeDoesNotMatch() {
        String solution = "def solution(): return \"Text, not Integer\"";

        Challenge challenge = new Challenge();

        challenge.setOutputType(DataType.Integer);

        TestCase testCase = new TestCase();
        testCase.setOutput("1");

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