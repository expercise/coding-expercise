package com.kodility.interpreter.python;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.TestCase;
import com.kodility.enums.DataType;
import com.kodility.interpreter.Interpreter;
import com.kodility.interpreter.InterpreterException;
import com.kodility.interpreter.InterpreterResult;
import com.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.python.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PythonInterpreter extends Interpreter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PythonInterpreter.class);

    private static final Map<DataType, Class<? extends PyObject>> TYPE_MAP = new HashMap<>();

    static {
        TYPE_MAP.put(DataType.Integer, PyInteger.class);
        TYPE_MAP.put(DataType.Text, PyString.class);
    }

    @Override
    protected void interpretInternal(ChallengeEvaluationContext context) throws InterpreterException {
        org.python.util.PythonInterpreter pythonInterpreter = new org.python.util.PythonInterpreter();

        executeSourceCode(pythonInterpreter, context.getSource());

        PyFunction solutionFunctionToCall = getSolutionFunctionToCall(pythonInterpreter);

        Challenge challenge = context.getChallenge();
        for (TestCase testCase : challenge.getTestCases()) {
            Object resultValue = makeFunctionCallAndGetResultValue(solutionFunctionToCall, challenge, testCase);

            if (!challenge.getOutputType().convert(testCase.getOutput()).equals(resultValue)) {
                context.setInterpreterResult(InterpreterResult.createFailedResult());
                return;
            }
        }

        context.setInterpreterResult(InterpreterResult.createSuccessResult());
    }

    private void executeSourceCode(org.python.util.PythonInterpreter pythonInterpreter, String sourceCode) throws InterpreterException {
        try {
            pythonInterpreter.exec(sourceCode);
        } catch (PySyntaxError e) {
            LOGGER.debug("Syntax error", e);
            throw new InterpreterException(InterpreterResult.syntaxErrorFailedResult());
        }
    }

    private PyFunction getSolutionFunctionToCall(org.python.util.PythonInterpreter pythonInterpreter) throws InterpreterException {
        PyStringMap locals = (PyStringMap) pythonInterpreter.getLocals();

        Optional<PyFunction> functionToCall = (Optional<PyFunction>) locals.values().stream()
                .filter(o -> o instanceof PyFunction)
                .filter(f -> "solution".equals(((PyFunction) f).getFuncName().asString()))
                .findFirst();

        if (!functionToCall.isPresent()) {
            throw new InterpreterException(InterpreterResult.noResultFailedResult());
        }

        return functionToCall.get();
    }

    private Object makeFunctionCallAndGetResultValue(PyFunction solutionFunctionToCall, Challenge challenge, TestCase testCase) throws InterpreterException {
        Object resultAsJavaObject = null;
        try {
            PyObject resultAsPyObject = solutionFunctionToCall.__call__(getArgumentsAsPyObjects(challenge, testCase));
            Class<? extends PyObject> outputType = TYPE_MAP.get(challenge.getOutputType());
            if (outputType.isAssignableFrom(PyInteger.class)) {
                resultAsJavaObject = resultAsPyObject.asInt();
            } else if (outputType.isAssignableFrom(PyString.class)) {
                resultAsJavaObject = resultAsPyObject.asString();
            }
        } catch (PyException e) {
            LOGGER.debug("Exception while function call", e);
            throw new InterpreterException(InterpreterResult.createFailedResult());
        }

        return resultAsJavaObject;
    }

    private PyObject[] getArgumentsAsPyObjects(Challenge challenge, TestCase testCase) throws InterpreterException {
        int argSize = testCase.getInputs().size();

        PyObject[] pyObjects = new PyObject[argSize];

        for (int i = 0; i < argSize; i++) {
            try {
                DataType type = challenge.getInputTypes().get(i).getInputType();
                Class<?> clazz = Class.forName(type.getClassName());
                Class<? extends PyObject> instanceType = TYPE_MAP.get(type);
                if (type.equals(DataType.Integer)) {
                    clazz = int.class;
                }
                Constructor<? extends PyObject> declaredConstructor = instanceType.getDeclaredConstructor(clazz);
                pyObjects[i] = declaredConstructor.newInstance(type.convert(testCase.getInputs().get(i).getInputValue()));
            } catch (Exception e) {
                LOGGER.debug("Exception while preparing arguments", e);
                throw new InterpreterException(InterpreterResult.noResultFailedResult());
            }
        }

        return pyObjects;
    }

}
