package com.expercise.service.challenge.action.postaction;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.ChallengeType;
import com.expercise.interpreter.TestCaseWithResult;
import com.expercise.service.challenge.UserTestCaseStateService;
import com.expercise.service.challenge.action.PostEvaluationAction;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveUserTestCaseStatePostAction implements PostEvaluationAction {

    @Autowired
    private UserTestCaseStateService userTestCaseStateService;

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        return context.getChallenge().getChallengeType() == ChallengeType.CODE_KATA;
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        Challenge challenge = context.getChallenge();
        List<TestCaseWithResult> testCaseWithResults = context.getTestCaseWithResults();
        userTestCaseStateService.saveUserState(challenge, context.getSource(), context.getLanguage(), testCaseWithResults);
    }

    @Override
    public int getPriority() {
        return PostEvaluationActionOrder.SAVE_USER_TEST_CASE_STATE.ordinal();
    }

}
