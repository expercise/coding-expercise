package com.expercise.interpreter;

import com.expercise.service.challenge.model.ChallengeEvaluationContext;

public class InfiniteLoopTestInterpreter extends Interpreter {

    @Override
    protected void interpretInternal(ChallengeEvaluationContext context) {
        while (true) {
            // Infinite loop
        }
    }

}
