package com.ufukuzun.kodility.interpreter.python;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class JythonInterpreterTest {

    private JythonInterpreter interpreter = new JythonInterpreter();

    @Test
    public void shouldEvaluatePythonFunction() {
        String result = interpreter.interpret("def foo(a, b): return a + b;", "foo(3,4)");

        assertThat(result, equalTo("7"));
    }

    @Test
    public void shouldSendErrorMessageWhenThereIsSyntaxError() {
        String result = interpreter.interpret("def foo(a, b): Keturn a -+ b;", "foo(3,4)");

        assertThat(result, equalTo("hata"));
    }

}
