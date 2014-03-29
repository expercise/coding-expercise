package com.ufukuzun.kodility.interpreter.python;

import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.i18n.MessageService;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.core.PySyntaxError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PythonInterpreter implements Interpreter {

    @Autowired
    private MessageService messageService;

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
            return InterpreterResult.createFailedResult(messageService.getMessage("interpreter.syntaxError"));
        }

        PyObject evaluationResult = pythonInterpreter.eval(testCode);

        if (evaluationResult instanceof PyNone) {
            return InterpreterResult.createFailedResult(messageService.getMessage("interpreter.noResult"));
        } else {
            return InterpreterResult.createSuccessResult(evaluationResult.toString());
        }
    }

}
