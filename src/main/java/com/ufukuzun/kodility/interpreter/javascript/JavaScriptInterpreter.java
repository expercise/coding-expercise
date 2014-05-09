package com.ufukuzun.kodility.interpreter.javascript;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.i18n.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

@Component
public class JavaScriptInterpreter implements Interpreter {

    @Autowired
    private MessageService messageService;

    @Override
    public boolean canInterpret(ProgrammingLanguage programmingLanguage) {
        return ProgrammingLanguage.JavaScript == programmingLanguage;
    }

    @Override
    public InterpreterResult interpret(String source, Challenge challenge) {
        ScriptEngine javaScriptEngine = getScriptEngine();

        try {
            javaScriptEngine.eval(source);
        } catch (ScriptException e) {
            return InterpreterResult.createFailedResult(prepareErrorMessage(e));
        }

        List<TestCase> testCases = challenge.getTestCases();
        for (TestCase testCase : testCases) {
            try {
                Object evaluationResult = ((Invocable) javaScriptEngine).invokeFunction("solution", testCase.getInputs().toArray());

                if (evaluationResult == null) {
                    return InterpreterResult.createFailedResult(messageService.getMessage("interpreter.noResult"));
                }

                boolean testCaseFailed = false;

                if (challenge.getOutputType().equals(DataType.Integer)) {
                    if (evaluationResult instanceof Number) {
                        Double evaluationResultAsDouble = (Double) evaluationResult;
                        testCaseFailed = !testCase.getOutput().equals(evaluationResultAsDouble.intValue());
                    }
                } else if (challenge.getOutputType().equals(DataType.Text)) {
                    String evaluationResultAsString = (String) evaluationResult;
                    testCaseFailed = !evaluationResultAsString.equals(evaluationResult);
                }

                if (testCaseFailed) {
                    return InterpreterResult.createFailedResult("");
                }
            } catch (Exception e) {
                return InterpreterResult.createFailedResult("");
            }
        }

        return InterpreterResult.createSuccessResult("");
    }

    private ScriptEngine getScriptEngine() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine javaScriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        javaScriptEngine.put(ScriptEngine.FILENAME, "solution.js");
        return javaScriptEngine;
    }

    private String prepareErrorMessage(ScriptException exception) {
        String message = exception.getMessage();
        String separator = ": ";
        int index = message.lastIndexOf(separator) + separator.length();
        return message.substring(index, message.length());
    }

}
