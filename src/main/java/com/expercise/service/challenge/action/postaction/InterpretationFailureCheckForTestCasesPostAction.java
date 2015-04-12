package com.expercise.service.challenge.action.postaction;

import com.expercise.interpreter.InterpreterResult;
import com.expercise.interpreter.TestCaseResult;
import com.expercise.interpreter.TestCaseWithResult;
import com.expercise.service.challenge.action.PostEvaluationAction;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import org.springframework.stereotype.Service;

@Service
public class InterpretationFailureCheckForTestCasesPostAction implements PostEvaluationAction {

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        InterpreterResult interpreterResult = context.getInterpreterResult();
        return !interpreterResult.isSuccess() && failedByLanguageSpecificError(interpreterResult);
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        for (TestCaseWithResult each : context.getTestCaseWithResults()) {
            each.setTestCaseResult(TestCaseResult.FAILED);
        }
    }

    @Override
    public int getPriority() {
        return PostEvaluationActionOrder.INTERPRETATION_FAILURE_CHECK_FOR_TEST_CASES.ordinal();
    }

    private boolean failedByLanguageSpecificError(InterpreterResult interpreterResult) {
        return interpreterResult.getFailureType() != null;
    }

}
