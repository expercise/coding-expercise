package com.ufukuzun.kodility.interpreter;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;

public interface Interpreter {

    boolean canInterpret(ProgrammingLanguage programmingLanguage);

    InterpreterResult interpret(String source, Challenge challenge);

}
