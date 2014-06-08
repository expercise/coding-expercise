package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.challenge.UserPointService;
import com.ufukuzun.kodility.service.challenge.action.PostEvaluationAction;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.challenge.model.SolutionValidationResult;
import com.ufukuzun.kodility.service.configuration.ConfigurationService;
import com.ufukuzun.kodility.service.i18n.MessageService;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolutionResponseCreationPostAction implements PostEvaluationAction {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ConfigurationService configurationService;

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        return true;
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        SolutionValidationResult result;

        InterpreterResult interpreterResult = context.getInterpreterResult();
        if (interpreterResult.isSuccess()) {
            int pointAmount = configurationService.getValueAsInteger("challenge.defaultpointamount");
            User user = authenticationService.getCurrentUser();
            if (userPointService.canUserWinPoint(context.getChallenge(), user)) {
                result = SolutionValidationResult.createSuccessResult(messageService.getMessage("challenge.success", pointAmount));
            } else {
                result = SolutionValidationResult.createSuccessResult(messageService.getMessage("challenge.successbutcannotwinpoint"));
            }
        } else {
            result = SolutionValidationResult.createFailedResult(messageService.getMessage("challenge.failed"));
        }

        context.setSolutionValidationResult(result);
    }

    @Override
    public int getPriority() {
        return 1;
    }

}
