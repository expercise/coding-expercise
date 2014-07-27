package com.ufukuzun.kodility.interpreter;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;

public interface Interpreter {

    void interpret(ChallengeEvaluationContext context) throws InterpreterException;

}
