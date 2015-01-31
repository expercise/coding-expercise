package com.expercise.interpreter;

import com.expercise.service.challenge.model.ChallengeEvaluationContext;

import java.util.concurrent.*;

public abstract class Interpreter {

    private static final int TIMEOUT_AS_SECONDS = 10;

    protected abstract void interpretInternal(ChallengeEvaluationContext context) throws InterpreterException;

    public final void interpret(ChallengeEvaluationContext context) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(() -> {
            interpretInternal(context);
            return null;
        });

        try {
            future.get(TIMEOUT_AS_SECONDS, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof InterpreterException) {
                context.setInterpreterResult(((InterpreterException) cause).getInterpreterResult());
            } else {
                context.setInterpreterResult(InterpreterResult.noResultFailedResult());
            }
        } finally {
            executor.shutdownNow();
        }
    }

}
