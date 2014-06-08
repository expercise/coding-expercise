package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.service.challenge.UserPointService;
import com.ufukuzun.kodility.service.challenge.action.PostEvaluationAction;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.user.AuthenticationService;
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
        return context.getInterpreterResult().isSuccess() && userPointService.canUserWinPoint(context.getChallenge(), currentUser);
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        userPointService.givePoint(context.getChallenge(), authenticationService.getCurrentUser());
    }

    @Override
    public int getPriority() {
        return 2;
    }

}
