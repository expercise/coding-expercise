package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.controller.challenge.model.UserSolutionModel;
import com.ufukuzun.kodility.service.challenge.SolutionService;
import com.ufukuzun.kodility.service.challenge.action.PostEvaluationAction;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PrepareChallengeSolutionsPostAction implements PostEvaluationAction {

    @Autowired
    private SolutionService solutionService;

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        return context.getInterpreterResult().isSuccess();
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        ArrayList<UserSolutionModel> userSolutionModels = solutionService.getUserSolutionModels(context.getChallenge());
        context.getSolutionValidationResult().setUserSolutionModels(userSolutionModels);
    }

    @Override
    public int getPriority() {
        return PostEvaluationActionOrder.PREPARE_CHALLENGE_SOLUTIONS.ordinal();
    }

}
