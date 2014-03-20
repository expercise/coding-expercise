package com.ufukuzun.kodility.interpreter.javascript;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class JavaScriptInterpreterTest {

    @InjectMocks
    private JavaScriptInterpreter interpreter;

    @Test
    public void shouldEvaluateFunctionCall() {
        Object result = interpreter.interpret("function foo(number) { return number++; } foo(2);");
        assertThat(Double.parseDouble(result.toString()), equalTo(2d));
    }

}
