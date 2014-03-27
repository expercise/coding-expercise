package com.ufukuzun.kodility.interpreter.python;

import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.core.PySyntaxError;
import org.springframework.stereotype.Service;

@Service
public class PythonInterpreter implements Interpreter {

    org.python.util.PythonInterpreter pythonInterpreter = new org.python.util.PythonInterpreter();

    @Override
    public boolean canInterpret(ProgrammingLanguage programmingLanguage) {
        return ProgrammingLanguage.Python == programmingLanguage;
    }

    @Override
    public InterpreterResult interpret(String solution, String testCode) {
        try {
            pythonInterpreter.exec(solution);
        } catch (PySyntaxError e) {
            return InterpreterResult.createFailedResult("Syntax Error");
        }

        PyObject evaluationResult = pythonInterpreter.eval(testCode);

        if (evaluationResult instanceof PyNone) {
            return InterpreterResult.createFailedResult("No Result");
        } else {
            return InterpreterResult.createSuccessResult(evaluationResult.toString());
        }
    }

}
