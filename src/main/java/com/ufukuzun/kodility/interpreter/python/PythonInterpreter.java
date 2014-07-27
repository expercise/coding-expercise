package com.ufukuzun.kodility.interpreter.python;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterException;
import com.ufukuzun.kodility.interpreter.InterpreterResultCreator;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.python.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

@Service
public class PythonInterpreter implements Interpreter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PythonInterpreter.class);

    private static final Map<DataType, Class<? extends PyObject>> TYPE_MAP = new HashMap<>();

    static {
        TYPE_MAP.put(DataType.Integer, PyInteger.class);
        TYPE_MAP.put(DataType.Text, PyString.class);
    }

    @Autowired
    private InterpreterResultCreator interpreterResultCreator;

    // TODO ufuk: concurrency issue on this field ?
    private org.python.util.PythonInterpreter pythonInterpreter = new org.python.util.PythonInterpreter();

    @Override
    public void interpret(ChallengeEvaluationContext context) throws InterpreterException {
        executeSourceCode(context.getSource());

        PyFunction solutionFunctionToCall = getSolutionFunctionToCall();

        Challenge challenge = context.getChallenge();
        for (TestCase testCase : challenge.getTestCases()) {
            Object resultValue = makeFunctionCallAndGetResultValue(solutionFunctionToCall, challenge, testCase);

            if (!challenge.getOutputType().convert(testCase.getOutput()).equals(resultValue)) {
                context.setInterpreterResult(interpreterResultCreator.noResultFailedResult());
                return;
            }
        }

        context.setInterpreterResult(interpreterResultCreator.successResult());
    }

    private void executeSourceCode(String sourceCode) throws InterpreterException {
        try {
            pythonInterpreter.exec(sourceCode);
        } catch (PySyntaxError e) {
            LOGGER.debug("Syntax error", e);
            throw new InterpreterException(interpreterResultCreator.syntaxErrorFailedResult());
        }
    }

    private PyFunction getSolutionFunctionToCall() throws InterpreterException {
        PyStringMap locals = (PyStringMap) pythonInterpreter.getLocals();

        PyFunction funcToCall = null;

        for (Object o : locals.values()) {
            if (o instanceof PyFunction) {
                PyFunction func = (PyFunction) o;
                if (func.getFuncName().getString().equals("solution")) {
                    funcToCall = func;
                }
            }
        }

        if (funcToCall == null) {
            throw new InterpreterException(interpreterResultCreator.noResultFailedResult());
        }

        return funcToCall;
    }

    private Object makeFunctionCallAndGetResultValue(PyFunction solutionFunctionToCall, Challenge challenge, TestCase testCase) throws InterpreterException {
        PyObject resultAsPyObject;
        try {
            resultAsPyObject = solutionFunctionToCall.__call__(getArgumentsAsPyObjects(challenge, testCase));
        } catch (PyException e) {
            LOGGER.debug("Exception while function call", e);
            throw new InterpreterException(interpreterResultCreator.failedResultWithoutMessage());
        }

        Object resultAsJavaObject = null;
        Class<? extends PyObject> outputType = TYPE_MAP.get(challenge.getOutputType());
        if (outputType.isAssignableFrom(PyInteger.class)) {
            resultAsJavaObject = resultAsPyObject.asInt();
        } else if (outputType.isAssignableFrom(PyString.class)) {
            resultAsJavaObject = resultAsPyObject.asString();
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
                throw new InterpreterException(interpreterResultCreator.noResultFailedResult());
            }
        }

        return pyObjects;
    }

}
