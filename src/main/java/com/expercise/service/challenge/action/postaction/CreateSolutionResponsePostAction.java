package com.expercise.service.challenge.action.postaction;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.ChallengeType;
import com.expercise.interpreter.InterpreterResult;
import com.expercise.interpreter.TestCaseModel;
import com.expercise.interpreter.TestCaseWithResult;
import com.expercise.service.challenge.LeaderBoardService;
import com.expercise.service.challenge.UserPointService;
import com.expercise.service.challenge.UserTestCaseStateService;
import com.expercise.service.challenge.action.PostEvaluationAction;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.challenge.model.SolutionValidationResult;
import com.expercise.service.i18n.MessageService;
import com.expercise.service.user.AuthenticationService;
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

    @Autowired
    private UserTestCaseStateService userTestCaseStateService;

    @Autowired
    private LeaderBoardService leaderBoardService;

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        return context.getChallenge().getChallengeType() == ChallengeType.ALGORITHM;
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        SolutionValidationResult result;

        InterpreterResult interpreterResult = context.getInterpreterResult();
        if (interpreterResult.isSuccess()) {
            Challenge challenge = context.getChallenge();
            if (userPointService.canUserWinPoint(challenge, context.getLanguage())) {
                Long rank = leaderBoardService.getRankFor(authenticationService.getCurrentUser());
                result = SolutionValidationResult.createSuccessResult(messageService.getMessage("challenge.successWithPoint", challenge.getPoint(), rank));
            } else {
                result = SolutionValidationResult.createSuccessResult(messageService.getMessage("challenge.success"));
            }
        } else {
            result = SolutionValidationResult.createFailedResult(messageService.getMessage("challenge.failed"));
            Optional.ofNullable(interpreterResult.getFailureType())
                    .ifPresent(ft -> result.addErrorDescriptionToResult(messageService.getMessage(ft.getMessageKey())));
        }

        userTestCaseStateService.saveUserState(context.getChallenge(), context.getSource(), context.getLanguage(), context.getTestCaseWithResults());

        result.getTestCasesWithSourceModel().setCurrentSourceCode(context.getSource());
        for (TestCaseWithResult testCaseWithResult : context.getTestCaseWithResults()) {
            result.addTestCaseModel(TestCaseModel.createFrom(testCaseWithResult));
        }

        context.setSolutionValidationResult(result);
    }

    @Override
    public int getPriority() {
        return PostEvaluationActionOrder.CREATE_SOLUTION_RESPONSE.ordinal();
    }

}
