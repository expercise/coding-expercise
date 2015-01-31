package com.expercise.interpreter.java;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.TestCase;
import com.expercise.enums.DataType;
import com.expercise.interpreter.Interpreter;
import com.expercise.interpreter.InterpreterException;
import com.expercise.interpreter.InterpreterResult;
import com.expercise.interpreter.java.compiler.InMemoryJavaCompiler;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
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

    public static Class getJavaClassOf(DataType dataType) {
        return TYPE_MAP.get(dataType);
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
        } finally {
            compiler.clean();
        }
    }

    private InMemoryJavaCompiler compile(ChallengeEvaluationContext context) throws InterpreterException {
        try {
            return new InMemoryJavaCompiler("Solution").compile(context.getSource());
        } catch (Exception e) {
            throw new InterpreterException(InterpreterResult.syntaxErrorFailedResult());
        }
    }

    private Class[] getParameterTypes(Challenge challenge) {
        return challenge.getInputTypes().stream()
                .map(it -> getJavaClassOf(it.getInputType()))
                .collect(Collectors.toList())
                .toArray(new Class[]{});
    }

}
