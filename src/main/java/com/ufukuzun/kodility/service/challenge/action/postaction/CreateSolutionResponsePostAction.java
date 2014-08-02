package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.challenge.UserPointService;
import com.ufukuzun.kodility.service.challenge.action.PostEvaluationAction;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.challenge.model.SolutionValidationResult;
import com.ufukuzun.kodility.service.i18n.MessageService;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateSolutionResponsePostAction implements PostEvaluationAction {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        return true;
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        SolutionValidationResult result;

        InterpreterResult interpreterResult = context.getInterpreterResult();
        if (interpreterResult.isSuccess()) {
            User user = authenticationService.getCurrentUser();
            Challenge challenge = context.getChallenge();
            if (userPointService.canUserWinPoint(challenge, user, context.getLanguage())) {
                result = SolutionValidationResult.createSuccessResult(messageService.getMessage("challenge.successwithpoint", challenge.getPoint()));
            } else {
                result = SolutionValidationResult.createSuccessResult(messageService.getMessage("challenge.success"));
            }
        } else {
            result = SolutionValidationResult.createFailedResult(messageService.getMessage("challenge.failed"));
        }

        context.setSolutionValidationResult(result);
    }

    @Override
    public int getPriority() {
        return PostEvaluationActionOrder.CREATE_SOLUTION_RESPONSE.ordinal();
    }

}
