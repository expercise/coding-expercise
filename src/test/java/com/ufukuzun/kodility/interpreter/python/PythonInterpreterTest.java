package com.ufukuzun.kodility.interpreter.python;

import com.ufukuzun.kodility.interpreter.InterpreterResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PythonInterpreterTest {

    @InjectMocks
    private PythonInterpreter interpreter;

    @Test
    public void shouldEvaluatePythonFunction() {
        InterpreterResult result = interpreter.interpret("def foo(a, b): return a + b;", "foo(3,4)");

        assertTrue(result.isSuccess());
        assertThat(result.getResult(), equalTo("7"));
    }

    @Test
    public void shouldSendErrorMessageWhenThereIsSyntaxError() {
        InterpreterResult result = interpreter.interpret("def foo(a, b): Keturn a -+ b;", "foo(3,4)");

        assertFalse(result.isSuccess());
        assertThat(result.getResult(), equalTo("Syntax Error"));
    }

    @Test
    public void shouldReturnNoResultErrorMessageIfTestCodeHasNoReturnValue() {
        InterpreterResult result = interpreter.interpret("def foo(): return;", "foo()");

        assertFalse(result.isSuccess());
        assertThat(result.getResult(), equalTo("No Result"));
    }

}
