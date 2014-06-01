package com.ufukuzun.kodility.interpreter.python;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.i18n.MessageService;
import org.python.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PythonInterpreter implements Interpreter {

    @Autowired
    private MessageService messageService;

    org.python.util.PythonInterpreter pythonInterpreter = new org.python.util.PythonInterpreter();

    Map<DataType, Class<? extends PyObject>> typeMap = new HashMap<DataType, Class<? extends PyObject>>() {{
        put(DataType.Integer, PyInteger.class);
        put(DataType.Text, PyString.class);
    }};

    @Override
    public void interpret(ChallengeEvaluationContext context) {
        Challenge challenge = context.getChallenge();
        String source = context.getSource();
        try {
            pythonInterpreter.exec(source);
        } catch (PySyntaxError e) {
            InterpreterResult failedResult = InterpreterResult.createFailedResult(messageService.getMessage("interpreter.syntaxError"));
            context.setInterpreterResult(failedResult);
            return;
        }

        PyStringMap locals = (PyStringMap) pythonInterpreter.getLocals();

        PyObject resultObject = null;

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
            InterpreterResult failedResult = InterpreterResult.createFailedResult(messageService.getMessage("interpreter.noResult"));
            context.setInterpreterResult(failedResult);
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
                    Class<? extends PyObject> instanceType = typeMap.get(type);
                    if (type.equals(DataType.Integer)) {
                        clazz = int.class;
                    }
                    Constructor<? extends PyObject> declaredConstructor = instanceType.getDeclaredConstructor(clazz);
                    pyObjects[i] = declaredConstructor.newInstance(type.convert(testCase.getInputs().get(i).getInputValue()));
                } catch (Exception e) {
                    InterpreterResult failedResult = InterpreterResult.createFailedResult(messageService.getMessage("interpreter.noResult"));
                    context.setInterpreterResult(failedResult);
                    return;
                }
            }

            try {
                resultObject = funcToCall.__call__(pyObjects);
            } catch (PyException e) {
                InterpreterResult failedResult = InterpreterResult.createFailedResult(e.value.asString());
                context.setInterpreterResult(failedResult);
                return;
            }

            Object value = null;
            Class<? extends PyObject> outputType = typeMap.get(challenge.getOutputType());
            if (outputType.isAssignableFrom(PyInteger.class)) {
                value = resultObject.asInt();
            } else if (outputType.isAssignableFrom(PyString.class)) {
                value = resultObject.asString();
            }

            if (!challenge.getOutputType().convert(testCase.getOutput()).equals(value)) {
                InterpreterResult failedResult = InterpreterResult.createFailedResult(messageService.getMessage("interpreter.noResult"));
                context.setInterpreterResult(failedResult);
                return;
            }

        }

        InterpreterResult successResult = InterpreterResult.createSuccessResult(resultObject.toString());
        context.setInterpreterResult(successResult);
    }

}
