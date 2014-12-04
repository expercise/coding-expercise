package com.kodility.service.challenge.action.postaction;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.user.User;
import com.kodility.interpreter.InterpreterResult;
import com.kodility.service.challenge.UserPointService;
import com.kodility.service.challenge.action.PostEvaluationAction;
import com.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.kodility.service.challenge.model.SolutionValidationResult;
import com.kodility.service.i18n.MessageService;
import com.kodility.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                result = SolutionValidationResult.createSuccessResult(messageService.getMessage("challenge.successWithPoint", challenge.getPoint()));
            } else {
                result = SolutionValidationResult.createSuccessResult(messageService.getMessage("challenge.success"));
            }
        } else {
            result = SolutionValidationResult.createFailedResult(messageService.getMessage("challenge.failed"));
            Optional.ofNullable(interpreterResult.getFailureType())
                    .ifPresent(ft -> result.addErrorDescriptionToResult(messageService.getMessage(ft.getMessageKey())));
        }

        context.setSolutionValidationResult(result);
    }

    @Override
    public int getPriority() {
        return PostEvaluationActionOrder.CREATE_SOLUTION_RESPONSE.ordinal();
    }

}
