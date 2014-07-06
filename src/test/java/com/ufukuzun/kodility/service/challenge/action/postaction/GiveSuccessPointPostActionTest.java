package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.challenge.UserPointService;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import com.ufukuzun.kodility.testutils.builder.ChallengeBuilder;
import com.ufukuzun.kodility.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GiveSuccessPointPostActionTest {

    @InjectMocks
    private GiveSuccessPointPostAction action;

    @Mock
    private UserPointService userPointService;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    public void shouldBeAbleToGiveUserPointWhenEvaluationIsSucceed() {
        InterpreterResult successResult = InterpreterResult.createSuccessResult();

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setInterpreterResult(successResult);
        context.setChallenge(new ChallengeBuilder().id(1L).approved(true).build());
        context.setLanguage(ProgrammingLanguage.Python);

        User user = new UserBuilder().id(1L).build();
        when(authenticationService.getCurrentUser()).thenReturn(user);
        when(userPointService.canUserWinPoint(context.getChallenge(), user, context.getLanguage())).thenReturn(true);

        assertTrue(action.canExecute(context));
    }

    @Test
    public void shouldNotBeAbleToGiveUserPointWhenUserHadAlreadyWonPointForThisChallengeBefore() {
        InterpreterResult successResult = InterpreterResult.createSuccessResult();

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setInterpreterResult(successResult);
        context.setChallenge(new ChallengeBuilder().id(1L).build());
        context.setLanguage(ProgrammingLanguage.Python);

        User user = new UserBuilder().id(1L).build();
        when(authenticationService.getCurrentUser()).thenReturn(user);
        when(userPointService.canUserWinPoint(context.getChallenge(), user, context.getLanguage())).thenReturn(false);

        assertFalse(action.canExecute(context));
    }

    @Test
    public void shouldNotBeAbleToGiveUserPointWhenEvaluationIsFailed() {
        InterpreterResult failedResult = InterpreterResult.createFailedResult();

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setInterpreterResult(failedResult);

        assertFalse(action.canExecute(context));
    }

    @Test
    public void shouldGiveUserChallengePointWhenEvaluationIsSucceed() {
        InterpreterResult successResult = InterpreterResult.createSuccessResult();

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(new Challenge());
        context.setLanguage(ProgrammingLanguage.Python);
        context.setInterpreterResult(successResult);

        User user = new UserBuilder().buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(user);

        action.execute(context);

        verify(userPointService).givePoint(context.getChallenge(), user, ProgrammingLanguage.Python);
    }

}
