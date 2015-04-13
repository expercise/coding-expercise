package com.expercise.interpreter.javascript;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.TestCase;
import com.expercise.enums.DataType;
import com.expercise.interpreter.*;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
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

        for (TestCaseWithResult eachTestCase : context.getTestCaseWithResults()) {
            Object resultValue = makeFunctionCallAndGetResultValue(javaScriptEngine, challenge, eachTestCase.getTestCaseUnderTest());
            processTestCase(eachTestCase, resultValue, challenge);
        }
        context.decideInterpreterResult();
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

    private void processTestCase(TestCaseWithResult testCaseWithResult, Object resultValue, Challenge challenge) {
        TestCaseResult testCaseResult = TestCaseResult.FAILED;

        if (challenge.getOutputType().equals(DataType.Integer)) {
            int evaluationResultAsInteger = ((Number) resultValue).intValue();
            int expectedValue = ((Number) Double.parseDouble(testCaseWithResult.getTestCaseUnderTest().getOutput())).intValue();
            testCaseResult = evaluationResultAsInteger == expectedValue ? TestCaseResult.PASSED : TestCaseResult.FAILED;
            testCaseWithResult.setActualValue(String.valueOf(evaluationResultAsInteger));
        } else if (challenge.getOutputType().equals(DataType.Text)) {
            String evaluationResultAsString = resultValue.toString();
            String expectedValue = testCaseWithResult.getTestCaseUnderTest().getOutput();
            testCaseResult = evaluationResultAsString.equals(expectedValue) ? TestCaseResult.PASSED : TestCaseResult.FAILED;
            testCaseWithResult.setActualValue(evaluationResultAsString);
        }

        testCaseWithResult.setTestCaseResult(testCaseResult);
    }

}
