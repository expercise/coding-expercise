package com.kodility.interpreter;

import com.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class InterpreterTest {

    @Test
    public void shouldLimitInterpretationTime() throws InterpreterException {
        InfiniteLoopTestInterpreter interpreter = new InfiniteLoopTestInterpreter();

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();

        interpreter.interpret(context);

        assertFalse(context.getInterpreterResult().isSuccess());
        assertThat(context.getInterpreterResult().getFailureType(), equalTo(InterpreterFailureType.NO_RESULT));
    }

}