package com.ufukuzun.kodility.interpreter;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;

public class InfiniteLoopTestInterpreter extends Interpreter {

    @Override
    protected void interpretInternal(ChallengeEvaluationContext context) throws InterpreterException {
        while (true) {
            // Infinite loop
        }
    }

}
