package com.ufukuzun.kodility.interpreter.javascript;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
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
    public void interpret(ChallengeEvaluationContext context) {
        Challenge challenge = context.getChallenge();
        String source = context.getSource();

        ScriptEngine javaScriptEngine = getScriptEngine();

        try {
            javaScriptEngine.eval(source);
        } catch (ScriptException e) {
            InterpreterResult failedResult = InterpreterResult.createFailedResult(prepareErrorMessage(e));
            context.setInterpreterResult(failedResult);
            return;
        }

        List<TestCase> testCases = challenge.getTestCases();
        for (TestCase testCase : testCases) {
            try {
                Object[] convertedInputValues = challenge.getConvertedInputValues(testCase.getInputs()).toArray();
                Object evaluationResult = ((Invocable) javaScriptEngine).invokeFunction("solution", convertedInputValues);

                if (evaluationResult == null) {
                    InterpreterResult failedResult = InterpreterResult.createFailedResult(messageService.getMessage("interpreter.noResult"));
                    context.setInterpreterResult(failedResult);
                    return;
                }

                boolean testCaseFailed = false;

                if (challenge.getOutputType().equals(DataType.Integer)) {
                    int evaluationResultAsInteger = ((Number) evaluationResult).intValue();
                    int expectedValue = ((Number) Double.parseDouble(testCase.getOutput())).intValue();
                    testCaseFailed = evaluationResultAsInteger != expectedValue;
                } else if (challenge.getOutputType().equals(DataType.Text)) {
                    String evaluationResultAsString = (String) evaluationResult;
                    testCaseFailed = !evaluationResultAsString.equals(evaluationResult);
                }

                if (testCaseFailed) {
                    InterpreterResult failedResult = InterpreterResult.createFailedResult("");
                    context.setInterpreterResult(failedResult);
                    return;
                }
            } catch (Exception e) {
                InterpreterResult failedResult = InterpreterResult.createFailedResult("");
                context.setInterpreterResult(failedResult);
                return;
            }
        }

        InterpreterResult successResult = InterpreterResult.createSuccessResult("");
        context.setInterpreterResult(successResult);
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
