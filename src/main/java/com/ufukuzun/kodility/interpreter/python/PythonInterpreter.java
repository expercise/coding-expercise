package com.ufukuzun.kodility.interpreter.python;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
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

    @Override
    public boolean canInterpret(ProgrammingLanguage programmingLanguage) {
        return ProgrammingLanguage.Python == programmingLanguage;
    }

    Map<Class<?>, Class<? extends PyObject>> typeMap = new HashMap<Class<?>, Class<? extends PyObject>>() {{
        put(Integer.class, PyInteger.class);
        put(String.class, PyString.class);
    }};

    public InterpreterResult interpret(String source, Challenge challenge) {
        try {
            pythonInterpreter.exec(source);
        } catch (PySyntaxError e) {
            return InterpreterResult.createFailedResult(messageService.getMessage("interpreter.syntaxError"));
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
            return InterpreterResult.createFailedResult(messageService.getMessage("interpreter.noResult"));
        }

        List<TestCase> testCases = challenge.getTestCases();

        for (TestCase testCase : testCases) {

            int argSize = testCase.getInputs().size();

            PyObject[] pyObjects = new PyObject[argSize];

            for (int i = 0; i < argSize; i++) {
                Class<?> type = challenge.getInputTypes().get(i);
                Class<? extends PyObject> instanceType = typeMap.get(type);
                if (type == Integer.class) {
                    type = int.class;
                }
                try {
                    Constructor<? extends PyObject> declaredConstructor = instanceType.getDeclaredConstructor(type);
                    pyObjects[i] = declaredConstructor.newInstance(testCase.getInputs().get(i));
                } catch (Exception e) {
                    return InterpreterResult.createFailedResult(messageService.getMessage("interpreter.noResult"));
                }
            }

            resultObject = funcToCall.__call__(pyObjects);
            Object value = null;
            Class<? extends PyObject> outputType = typeMap.get(challenge.getOutputType());
            if (outputType.isAssignableFrom(PyInteger.class)) {
                value = resultObject.__tojava__(Integer.class);
            } else if (outputType.isAssignableFrom(PyString.class)) {
                value = resultObject.__tojava__(String.class);
            }

            if (!testCase.getOutput().equals(value)) {
                return InterpreterResult.createFailedResult(messageService.getMessage("interpreter.noResult"));
            }

        }

        return InterpreterResult.createSuccessResult(resultObject.toString());
    }

}
