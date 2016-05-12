package com.expercise.service.challenge.action;

import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.util.Prioritized;

public interface PostEvaluationAction extends Prioritized {

    boolean canExecute(ChallengeEvaluationContext context);

    void execute(ChallengeEvaluationContext context);

    enum PostEvaluationActionOrder {

        INTERPRETATION_FAILURE_CHECK_FOR_TEST_CASES,
        SAVE_USER_TEST_CASE_STATE,
        CREATE_SOLUTION_RESPONSE,
        CREATE_KATA_SOLUTION_RESPONSE,
        GIVE_SUCCESS_POINT,
        SAVE_USER_SOLUTION,
        PREPARE_CHALLENGE_USER_SOLUTIONS,
        PREPARE_CONSOLE_MESSAGE_RESPONSE

    }

}
