package com.kodility.interpreter.javascript;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.TestCase;
import com.kodility.enums.DataType;
import com.kodility.interpreter.Interpreter;
import com.kodility.interpreter.InterpreterException;
import com.kodility.interpreter.InterpreterResult;
import com.kodility.service.challenge.model.ChallengeEvaluationContext;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

@Component
public class JavaScriptInterpreter extends Interpreter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaScriptInterpreter.class);

    @Override
    protected void interpretInternal(ChallengeEvaluationContext context) throws InterpreterException {
        ScriptEngine javaScriptEngine = getScriptEngine();

        evaluateSourceCode(context.getSource(), javaScriptEngine);

        Challenge challenge = context.getChallenge();
        for (TestCase testCase : challenge.getTestCases()) {
            Object resultValue = makeFunctionCallAndGetResultValue(javaScriptEngine, challenge, testCase);
            if (isTestCaseFailedByResult(challenge, testCase, resultValue)) {
                context.setInterpreterResult(InterpreterResult.createFailedResult());
                return;
            }
        }

        context.setInterpreterResult(InterpreterResult.createSuccessResult());
    }

    private ScriptEngine getScriptEngine() {
        NashornScriptEngine javaScriptEngine = (NashornScriptEngine) new NashornScriptEngineFactory().getScriptEngine(new String[]{"-strict", "--no-java", "--no-syntax-extensions"});
        javaScriptEngine.put(ScriptEngine.FILENAME, "solution.js");
        return javaScriptEngine;
    }

    private void evaluateSourceCode(String sourceCode, ScriptEngine javaScriptEngine) throws InterpreterException {
        try {
            javaScriptEngine.eval(sourceCode);
        } catch (ScriptException e) {
            LOGGER.debug("Exception while evaluation", e);
            throw new InterpreterException(InterpreterResult.syntaxErrorFailedResult());
        }
    }

    private Object makeFunctionCallAndGetResultValue(ScriptEngine javaScriptEngine, Challenge challenge, TestCase testCase) throws InterpreterException {
        Object evaluationResult;

        try {
            Object[] convertedInputValues = challenge.getConvertedInputValues(testCase.getInputs()).toArray();
            evaluationResult = ((Invocable) javaScriptEngine).invokeFunction("solution", convertedInputValues);
        } catch (Exception e) {
            LOGGER.debug("Exception while interpreting", e);
            throw new InterpreterException(InterpreterResult.createFailedResult());
        }

        if (evaluationResult == null) {
            throw new InterpreterException(InterpreterResult.noResultFailedResult());
        }

        return evaluationResult;
    }

    private boolean isTestCaseFailedByResult(Challenge challenge, TestCase testCase, Object resultValue) {
        boolean testCaseFailed = false;

        if (challenge.getOutputType().equals(DataType.Integer)) {
            int evaluationResultAsInteger = ((Number) resultValue).intValue();
            int expectedValue = ((Number) Double.parseDouble(testCase.getOutput())).intValue();
            testCaseFailed = evaluationResultAsInteger != expectedValue;
        } else if (challenge.getOutputType().equals(DataType.Text)) {
            String evaluationResultAsString = (String) resultValue;
            testCaseFailed = !evaluationResultAsString.equals(resultValue);
        }

        return testCaseFailed;
    }

}
