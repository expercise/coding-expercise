package com.expercise.interpreter.javascript;

import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.challenge.TestCaseInputValue;
import com.expercise.enums.DataType;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.*;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JavaScriptInterpreter extends Interpreter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaScriptInterpreter.class);

    @Autowired
    private InterpreterClient interpreterClient;

    @Override
    protected void interpretInternal(ChallengeEvaluationContext context) throws InterpreterException {
        for(TestCaseWithResult eachTestCaseWithResult : context.getTestCaseWithResults()) {
            TestCase eachTestCase = eachTestCaseWithResult.getTestCaseUnderTest();
            String resultValue = makeFunctionCallAndGetResultValue(context.getSource(), eachTestCase);
            processTestCase(eachTestCaseWithResult, resultValue);
        }
        context.decideInterpreterResult();
    }

    private String makeFunctionCallAndGetResultValue(String sourceCode, TestCase testCase) throws InterpreterException {
        String params = testCase.getInputs().stream().map(TestCaseInputValue::getInputValue).collect(Collectors.joining(","));
        sourceCode += "\nconsole.log(solution(" + params + "))";
        InterpretResponse resp = interpreterClient.interpret(new InterpretRequest(sourceCode, ProgrammingLanguage.JavaScript.name()));
        //todo: handle also stderr stream
        return resp.getStdOut();
    }

    private void processTestCase(TestCaseWithResult testCaseWithResult, String resultValue) {
        TestCaseResult testCaseResult = TestCaseResult.FAILED;

        DataType outputType = testCaseWithResult.getTestCaseUnderTest().getOutputType();
        Object expectedJavaObject = outputType.toJavaObject(testCaseWithResult.getTestCaseUnderTest().getOutput());
        if (outputType == DataType.Integer) {
            double evaluationResultAsNumber = Double.valueOf(resultValue);
            double expectedValue = Double.parseDouble(expectedJavaObject.toString());
            testCaseResult = evaluationResultAsNumber == expectedValue
                    ? TestCaseResult.PASSED
                    : TestCaseResult.FAILED;
            testCaseWithResult.setActualValue(String.valueOf(evaluationResultAsNumber));
        } else if (outputType == DataType.Text) {
            testCaseResult = resultValue.equals(expectedJavaObject)
                    ? TestCaseResult.PASSED
                    : TestCaseResult.FAILED;
            testCaseWithResult.setActualValue(DataType.toLiteral(resultValue));
        } else if (outputType == DataType.Array) {
            Collection<Object> resultCollection = JsonUtils.fromJson(resultValue, Collection.class);
            testCaseResult = resultCollection.equals(expectedJavaObject)
                    ? TestCaseResult.PASSED
                    : TestCaseResult.FAILED;
            testCaseWithResult.setActualValue(JsonUtils.format(convertCollectionToLiteral(resultCollection).toString()));
        }

        testCaseWithResult.setTestCaseResult(testCaseResult);
        testCaseWithResult.setResultMessage(OutputMessageAggregator.getOutputMessage());

    }

    private Collection<Object> convertCollectionToLiteral(Collection<Object> source) {
        return source.stream().map(obj -> {
            if (obj instanceof Collection) {
                return convertCollectionToLiteral((Collection<Object>) obj);
            } else {
                return DataType.toLiteral(obj);
            }
        }).collect(Collectors.toList());
    }
}
