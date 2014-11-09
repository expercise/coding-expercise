package com.ufukuzun.kodility.interpreter.java;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterException;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.interpreter.java.compiler.InMemoryJavaCompiler;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JavaInterpreter extends Interpreter {

    private static final Map<DataType, Class<?>> TYPE_MAP = new HashMap<>();

    static {
        TYPE_MAP.put(DataType.Integer, Integer.class);
        TYPE_MAP.put(DataType.Text, String.class);
    }

    @Override
    protected void interpretInternal(ChallengeEvaluationContext context) throws InterpreterException {
        InMemoryJavaCompiler compiler = compile(context);

        try {
            Challenge challenge = context.getChallenge();

            for (TestCase testCase : challenge.getTestCases()) {
                Object[] args = challenge.getConvertedInputValues(testCase.getInputs()).toArray();
                Class[] parameterTypes = getParameterTypes(challenge);
                Object result = compiler.invoke("solution", args, parameterTypes);
                if (!challenge.getOutputValueFor(testCase).equals(result)) {
                    context.setInterpreterResult(InterpreterResult.createFailedResult());
                    return;
                }
            }

            context.setInterpreterResult(InterpreterResult.createSuccessResult());
        } catch (Exception e) {
            context.setInterpreterResult(InterpreterResult.createFailedResult());
        }
    }

    private InMemoryJavaCompiler compile(ChallengeEvaluationContext context) throws InterpreterException {
        try {
            return new InMemoryJavaCompiler("Solution").compile(context.getSource());
        } catch (Exception e) {
            throw new InterpreterException(InterpreterResult.noResultFailedResult());
        }
    }

    private Class[] getParameterTypes(Challenge challenge) {
        return challenge.getInputTypes().stream()
                .map(it -> TYPE_MAP.get(it.getInputType()))
                .collect(Collectors.toList())
                .toArray(new Class[]{});
    }

}
