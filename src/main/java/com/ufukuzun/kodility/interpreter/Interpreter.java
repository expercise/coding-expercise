package com.ufukuzun.kodility.interpreter;

import com.ufukuzun.kodility.controller.challenge.model.SolutionFromUser;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;

public interface Interpreter {

    boolean canInterpret(ProgrammingLanguage programmingLanguage);

    InterpreterResult interpret(Solution solutionForChallenge, SolutionFromUser solutionFromUser);

}
