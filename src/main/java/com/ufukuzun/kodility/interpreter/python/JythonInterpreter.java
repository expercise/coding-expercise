package com.ufukuzun.kodility.interpreter.python;

import org.python.core.PyObject;
import org.python.core.PySyntaxError;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

@Service
public class JythonInterpreter {

    PythonInterpreter pythonInterpreter = new PythonInterpreter();

    public String interpret(String script, String test) {
        try {
            pythonInterpreter.exec(script);
        } catch (PySyntaxError e) {
            return e.toString();
        }

        PyObject eval = pythonInterpreter.eval(test);

        return eval.toString();
    }

}
