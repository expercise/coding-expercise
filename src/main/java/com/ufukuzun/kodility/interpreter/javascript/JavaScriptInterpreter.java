package com.ufukuzun.kodility.interpreter.javascript;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResultCreator;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

@Component
public class JavaScriptInterpreter implements Interpreter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaScriptInterpreter.class);

    @Autowired
    private InterpreterResultCreator interpreterResultCreator;

    @Override
    public void interpret(ChallengeEvaluationContext context) {
        Challenge challenge = context.getChallenge();
        String source = context.getSource();

        ScriptEngine javaScriptEngine = getScriptEngine();

        try {
            javaScriptEngine.eval(source);
        } catch (ScriptException e) {
            LOGGER.debug("Exception while evaluation", e);
            context.setInterpreterResult(interpreterResultCreator.syntaxErrorFailedResult());
            return;
        }

        List<TestCase> testCases = challenge.getTestCases();
        for (TestCase testCase : testCases) {
            try {
                Object[] convertedInputValues = challenge.getConvertedInputValues(testCase.getInputs()).toArray();
                Object evaluationResult = ((Invocable) javaScriptEngine).invokeFunction("solution", convertedInputValues);

                if (evaluationResult == null) {
                    context.setInterpreterResult(interpreterResultCreator.noResultFailedResult());
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
                    context.setInterpreterResult(interpreterResultCreator.failedResultWithoutMessage());
                    return;
                }
            } catch (Exception e) {
                LOGGER.debug("Exception while interpreting", e);
                context.setInterpreterResult(interpreterResultCreator.failedResultWithoutMessage());
                return;
            }
        }

        context.setInterpreterResult(interpreterResultCreator.successResult());
    }

    private ScriptEngine getScriptEngine() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine javaScriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        javaScriptEngine.put(ScriptEngine.FILENAME, "solution.js");
        return javaScriptEngine;
    }

}
