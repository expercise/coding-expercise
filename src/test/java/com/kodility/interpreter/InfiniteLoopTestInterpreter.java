package com.kodility.interpreter;

import com.kodility.service.challenge.model.ChallengeEvaluationContext;

public class InfiniteLoopTestInterpreter extends Interpreter {

    @Override
    protected void interpretInternal(ChallengeEvaluationContext context) throws InterpreterException {
        while (true) {
            // Infinite loop
        }
    }

}
