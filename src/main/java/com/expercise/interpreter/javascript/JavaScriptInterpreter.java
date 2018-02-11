package com.expercise.interpreter.javascript;

import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.challenge.TestCaseInputValue;
import com.expercise.enums.DataType;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.*;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
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
    protected void interpretInternal(ChallengeEvaluationContext context) {
        for(TestCaseWithResult eachTestCaseWithResult : context.getTestCaseWithResults()) {
            TestCase eachTestCase = eachTestCaseWithResult.getTestCaseUnderTest();
            InterpretResponse interpretResponse = wrapWithFunctionCallAndGetResult(context.getSource(), eachTestCase);
            if (StringUtils.isNotBlank(interpretResponse.getStdErr())) {
                eachTestCaseWithResult.setResultMessage(interpretResponse.getStdErr());
            }
            processTestCase(eachTestCaseWithResult, interpretResponse.getStdOut());
        }
        context.decideInterpreterResult();
    }

    private InterpretResponse wrapWithFunctionCallAndGetResult(String sourceCode, TestCase testCase) {
        String params = testCase.getInputs().stream().map(TestCaseInputValue::getInputValue).collect(Collectors.joining(","));
        String sourceCodes = sourceCode + "\nconsole.log(solution(" + params + "))";
        return interpreterClient.interpret(new InterpretRequest(sourceCodes, ProgrammingLanguage.JavaScript.name()));
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
