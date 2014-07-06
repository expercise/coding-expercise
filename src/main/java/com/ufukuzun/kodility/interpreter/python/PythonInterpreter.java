package com.ufukuzun.kodility.interpreter.python;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResultCreator;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.python.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PythonInterpreter implements Interpreter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PythonInterpreter.class);

    private static final Map<DataType, Class<? extends PyObject>> TYPE_MAP = new HashMap<DataType, Class<? extends PyObject>>() {{
        put(DataType.Integer, PyInteger.class);
        put(DataType.Text, PyString.class);
    }};

    @Autowired
    private InterpreterResultCreator interpreterResultCreator;

    org.python.util.PythonInterpreter pythonInterpreter = new org.python.util.PythonInterpreter();

    @Override
    public void interpret(ChallengeEvaluationContext context) {
        Challenge challenge = context.getChallenge();
        String source = context.getSource();
        try {
            pythonInterpreter.exec(source);
        } catch (PySyntaxError e) {
            LOGGER.debug("Syntax error", e);
            context.setInterpreterResult(interpreterResultCreator.syntaxErrorFailedResult());
            return;
        }

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
            context.setInterpreterResult(interpreterResultCreator.noResultFailedResult());
            return;
        }

        List<TestCase> testCases = challenge.getTestCases();

        for (TestCase testCase : testCases) {
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
                    context.setInterpreterResult(interpreterResultCreator.noResultFailedResult());
                    return;
                }
            }

            PyObject resultObject;
            try {
                resultObject = funcToCall.__call__(pyObjects);
            } catch (PyException e) {
                LOGGER.debug("Exception while function call", e);
                context.setInterpreterResult(interpreterResultCreator.failedResultWithoutMessage());
                return;
            }

            Object value = null;
            Class<? extends PyObject> outputType = TYPE_MAP.get(challenge.getOutputType());
            if (outputType.isAssignableFrom(PyInteger.class)) {
                value = resultObject.asInt();
            } else if (outputType.isAssignableFrom(PyString.class)) {
                value = resultObject.asString();
            }

            if (!challenge.getOutputType().convert(testCase.getOutput()).equals(value)) {
                context.setInterpreterResult(interpreterResultCreator.noResultFailedResult());
                return;
            }
        }

        context.setInterpreterResult(interpreterResultCreator.successResult());
    }

}
