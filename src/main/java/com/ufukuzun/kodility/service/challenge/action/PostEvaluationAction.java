package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.util.Prioritized;

public interface PostEvaluationAction extends Prioritized {

    boolean canExecute(ChallengeEvaluationContext context);

    void execute(ChallengeEvaluationContext context);

}
