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

    Map<String, Class<? extends PyObject>> typeMap = new HashMap<String, Class<? extends PyObject>>() {{
        put(Integer.class.getName(), PyInteger.class);
        put(String.class.getName(), PyString.class);
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
                try {
                    String type = challenge.getInputTypes().get(i);
                    Class<?> clazz = Class.forName(type);
                    Class<? extends PyObject> instanceType = typeMap.get(type);
                    if (type.equals(Integer.class.getName())) {
                        clazz = int.class;
                    }
                    Constructor<? extends PyObject> declaredConstructor = instanceType.getDeclaredConstructor(clazz);
                    pyObjects[i] = declaredConstructor.newInstance(testCase.getInputs().get(i));
                } catch (Exception e) {
                    return InterpreterResult.createFailedResult(messageService.getMessage("interpreter.noResult"));
                }
            }

            try {
            resultObject = funcToCall.__call__(pyObjects);
            } catch (PyException e) {
                return InterpreterResult.createFailedResult(e.value.asString());
            }
            
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
