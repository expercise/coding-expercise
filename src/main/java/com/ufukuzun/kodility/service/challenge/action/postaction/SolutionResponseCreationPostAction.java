package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.challenge.action.PostEvaluationAction;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.challenge.model.SolutionValidationResult;
import com.ufukuzun.kodility.service.i18n.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolutionResponseCreationPostAction implements PostEvaluationAction {

    @Autowired
    private MessageService messageService;

    @Override
    public void execute(ChallengeEvaluationContext context) {
        SolutionValidationResult result;

        InterpreterResult interpreterResult = context.getInterpreterResult();
        if (interpreterResult.isSuccess()) {
            result = SolutionValidationResult.createSuccessResult(messageService.getMessage("challenge.success"));
        } else {
            result = SolutionValidationResult.createFailedResult(messageService.getMessage("challenge.failed"));
        }

        context.setSolutionValidationResult(result);
    }

}
