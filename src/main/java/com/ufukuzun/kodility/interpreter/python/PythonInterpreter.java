package com.ufukuzun.kodility.interpreter.python;

import com.ufukuzun.kodility.controller.challenge.model.SolutionFromUser;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import org.springframework.stereotype.Service;

@Service
public class PythonInterpreter implements Interpreter {

    org.python.util.PythonInterpreter pythonInterpreter = new org.python.util.PythonInterpreter();

    @Override
    public boolean canInterpret(ProgrammingLanguage programmingLanguage) {
        return ProgrammingLanguage.Python == programmingLanguage;
    }

    // TODO ufuk: complete
    @Override
    public InterpreterResult interpret(Solution solutionForChallenge, SolutionFromUser solutionFromUser) {
//        try {
//            pythonInterpreter.exec(script);
//        } catch (PySyntaxError e) {
//            return e.toString();
//        }
//
//        PyObject eval = pythonInterpreter.eval(test);
//
//        return eval.toString();

        return InterpreterResult.createSuccessResult("Success - Python");
    }

}
