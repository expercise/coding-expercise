package com.ufukuzun.kodility.interpreter.javascript;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
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

        List<String> inputTypes = new ArrayList<String>();
        inputTypes.add(Integer.class.getName());
        inputTypes.add(Integer.class.getName());

        challenge.setInputTypes(inputTypes);
        challenge.setOutputType(Integer.class.getName());

        TestCase testCase = new TestCase();

        List<Object> inputValues = new ArrayList<Object>();
        inputValues.add(12);
        inputValues.add(23);
        testCase.setInputs(inputValues);
        testCase.setOutput(35);

        challenge.addTestCase(testCase);

        InterpreterResult interpreterResult = interpreter.interpret(sumSolution, challenge);

        assertTrue(interpreterResult.isSuccess());
    }

    @Test
    public void shouldReturnExceptionMessageIfEvaluationCauseAnException() {
        InterpreterResult result = interpreter.interpret("fuNksin fuu(){}", new Challenge());

        assertFalse(result.isSuccess());
        assertThat(result.getResult(), equalTo("missing ; before statement (solution.js#1) in solution.js at line number 1"));
    }

}
