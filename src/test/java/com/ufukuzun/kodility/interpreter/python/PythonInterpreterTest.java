package com.ufukuzun.kodility.interpreter.python;

import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.i18n.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    public void shouldEvaluatePythonFunction() {
        InterpreterResult result = interpreter.interpret("def foo(a, b): return a + b;", "foo(3,4)");

        assertTrue(result.isSuccess());
        assertThat(result.getResult(), equalTo("7"));
    }

    @Test
    public void shouldSendErrorMessageWhenThereIsSyntaxError() {
        when(messageService.getMessage("interpreter.syntaxError")).thenReturn("Syntax Error");

        InterpreterResult result = interpreter.interpret("def foo(a, b): Keturn a -+ b;", "foo(3,4)");

        assertFalse(result.isSuccess());
        assertThat(result.getResult(), equalTo("Syntax Error"));
    }

    @Test
    public void shouldReturnNoResultErrorMessageIfTestCodeHasNoReturnValue() {
        when(messageService.getMessage("interpreter.noResult")).thenReturn("No Result");

        InterpreterResult result = interpreter.interpret("def foo(): return;", "foo()");

        assertFalse(result.isSuccess());
        assertThat(result.getResult(), equalTo("No Result"));
    }

}
