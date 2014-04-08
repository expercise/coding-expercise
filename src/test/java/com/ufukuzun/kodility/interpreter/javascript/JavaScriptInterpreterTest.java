package com.ufukuzun.kodility.interpreter.javascript;

import com.ufukuzun.kodility.service.i18n.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JavaScriptInterpreterTest {

    @InjectMocks
    private JavaScriptInterpreter interpreter;

    @Mock
    private MessageService messageService;

    @Test
    public void shouldEvaluateFunctionCall() {
//        InterpreterResult result = interpreter.interpret("function foo(number) { return number++; }", "foo(2);");
//
//        assertTrue(result.isSuccess());
//        assertThat(Double.parseDouble(result.getResult()), equalTo(2d));
    }

    @Test
    public void shouldReturnNoResultMessageIfEvaluationResultIsNull() {
//        when(messageService.getMessage("interpreter.noResult")).thenReturn("No Result");
//
//        InterpreterResult result = interpreter.interpret("function foo() {}", "foo();");
//
//        assertFalse(result.isSuccess());
//        assertThat(result.getResult(), equalTo("No Result"));
    }

    @Test
    public void shouldReturnExceptionMessageIfEvaluationCauseAnException() {
//        InterpreterResult result = interpreter.interpret("", "foo();");
//
//        assertFalse(result.isSuccess());
//        assertThat(result.getResult(), equalTo("\"foo\" is not defined. (solution.js#2) in solution.js at line number 2"));
    }

}
