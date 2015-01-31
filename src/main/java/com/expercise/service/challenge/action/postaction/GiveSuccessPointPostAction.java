package com.expercise.service.challenge.action.postaction;

import com.expercise.domain.user.User;
import com.expercise.service.challenge.UserPointService;
import com.expercise.service.challenge.action.PostEvaluationAction;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiveSuccessPointPostAction implements PostEvaluationAction {

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        User currentUser = authenticationService.getCurrentUser();
        return context.getInterpreterResult().isSuccess() && userPointService.canUserWinPoint(context.getChallenge(), currentUser, context.getLanguage());
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        userPointService.givePoint(context.getChallenge(), authenticationService.getCurrentUser(), context.getLanguage());
    }

    @Override
    public int getPriority() {
        return PostEvaluationActionOrder.GIVE_SUCCESS_POINT.ordinal();
    }

}
