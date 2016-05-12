package com.expercise.service.challenge.action.postaction;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.InterpreterResult;
import com.expercise.interpreter.TestCaseWithResult;
import com.expercise.service.challenge.UserPointService;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.user.AuthenticationService;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GiveSuccessPointPostActionTest {

    @InjectMocks
    private GiveSuccessPointPostAction action;

    @Mock
    private UserPointService userPointService;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    public void shouldBeAbleToGiveUserPointWhenAllTestCasesEvaluationIsSucceedAndUserIsAuthenticated() {
        TestCase testCase = new TestCase();
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setInterpreterResult(InterpreterResult.createSuccessResult());
        context.setChallenge(new ChallengeBuilder().id(1L).approved(true).testCases(testCase).build());
        context.addTestCaseWithResult(new TestCaseWithResult(testCase));

        when(authenticationService.isCurrentUserAuthenticated()).thenReturn(true);
        when(userPointService.canUserWinPoint(context.getChallenge(), context.getLanguage())).thenReturn(true);

        assertTrue(action.canExecute(context));
    }

    @Test
    public void shouldNotBeAbleToGiveUserPointWhenUserHadAlreadyWonPointForThisChallengeBefore() {
        InterpreterResult successResult = InterpreterResult.createSuccessResult();

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setInterpreterResult(successResult);
        context.setChallenge(new ChallengeBuilder().id(1L).build());
        context.setLanguage(ProgrammingLanguage.Python);

        when(authenticationService.isCurrentUserAuthenticated()).thenReturn(true);
        when(userPointService.canUserWinPoint(context.getChallenge(), context.getLanguage())).thenReturn(false);

        assertFalse(action.canExecute(context));
    }

    @Test
    public void shouldNotBeAbleToGiveUserPointWhenUserIsNotAuthenticatedEvenIfAllTestCasesEvaluationIsSucceed() {
        TestCase testCase = new TestCase();
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setInterpreterResult(InterpreterResult.createSuccessResult());
        context.setChallenge(new ChallengeBuilder().id(1L).approved(true).testCases(testCase).build());
        context.addTestCaseWithResult(new TestCaseWithResult(testCase));

        when(authenticationService.isCurrentUserAuthenticated()).thenReturn(false);

        assertFalse(action.canExecute(context));

        verifyZeroInteractions(userPointService);
    }

    @Test
    public void shouldNotBeAbleToGiveUserPointWhenEvaluationIsFailed() {
        TestCase testCase0 = new TestCase();
        TestCase testCase1 = new TestCase();

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.addTestCaseWithResult(new TestCaseWithResult(testCase0));
        context.addTestCaseWithResult(new TestCaseWithResult(testCase1));

        Challenge challenge = new ChallengeBuilder().id(2L).build();
        challenge.addTestCase(testCase0);
        challenge.addTestCase(testCase1);

        context.setChallenge(challenge);

        context.setInterpreterResult(InterpreterResult.createFailedResult());

        assertFalse(action.canExecute(context));
    }

    @Test
    public void shouldGiveUserChallengePointWhenChallengeIsCompleted() {
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
