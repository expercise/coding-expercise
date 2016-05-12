package com.expercise.service.challenge.action.postaction;

import com.expercise.controller.challenge.model.UserSolutionModel;
import com.expercise.service.challenge.SolutionService;
import com.expercise.service.challenge.action.PostEvaluationAction;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrepareChallengeUserSolutionsPostAction implements PostEvaluationAction {

    @Autowired
    private SolutionService solutionService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        return authenticationService.isCurrentUserAuthenticated() && context.isChallengeCompleted();
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        List<UserSolutionModel> userSolutionModels = solutionService.getUserSolutionModels(context.getChallenge());
        context.getSolutionValidationResult().setUserSolutionModels(userSolutionModels);
    }

    @Override
    public int getPriority() {
        return PostEvaluationActionOrder.PREPARE_CHALLENGE_USER_SOLUTIONS.ordinal();
    }

}
