package com.kodility.service.challenge.action;

import com.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.kodility.service.util.Prioritized;

public interface PostEvaluationAction extends Prioritized {

    boolean canExecute(ChallengeEvaluationContext context);

    void execute(ChallengeEvaluationContext context);

    enum PostEvaluationActionOrder {

        CREATE_SOLUTION_RESPONSE,
        GIVE_SUCCESS_POINT,
        SAVE_USER_SOLUTION,
        PREPARE_CHALLENGE_SOLUTIONS

    }

}
