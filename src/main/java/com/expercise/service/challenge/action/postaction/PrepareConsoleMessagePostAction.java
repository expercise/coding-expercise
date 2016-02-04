package com.expercise.service.challenge.action.postaction;

import com.expercise.service.challenge.action.PostEvaluationAction;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class PrepareConsoleMessagePostAction implements PostEvaluationAction {

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        return StringUtils.isNotBlank(context.getInterpreterResult().getConsoleMessage());
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        context.getSolutionValidationResult().setConsoleMessage(
                context.getInterpreterResult().getConsoleMessage()
        );
    }

    @Override
    public int getPriority() {
        return PostEvaluationActionOrder.PREPARE_CONSOLE_MESSAGE_RESPONSE.ordinal();
    }

}
