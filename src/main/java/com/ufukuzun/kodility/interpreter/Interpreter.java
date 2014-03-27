package com.ufukuzun.kodility.interpreter;

import com.ufukuzun.kodility.enums.ProgrammingLanguage;

public interface Interpreter {

    boolean canInterpret(ProgrammingLanguage programmingLanguage);

    InterpreterResult interpret(String solution, String testCode);

}
