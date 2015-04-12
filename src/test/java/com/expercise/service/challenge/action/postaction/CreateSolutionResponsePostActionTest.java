package com.expercise.service.challenge.action.postaction;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.ChallengeType;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.InterpreterFailureType;
import com.expercise.interpreter.InterpreterResult;
import com.expercise.service.challenge.UserPointService;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.i18n.MessageService;
import com.expercise.service.user.AuthenticationService;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateSolutionResponsePostActionTest {

    @InjectMocks
    private CreateSolutionResponsePostAction action;

    @Mock
    private MessageService messageService;

    @Mock
    private UserPointService userPointService;

    @Mock
    private AuthenticationService authenticationService;

    private ChallengeEvaluationContext context;

    private User user;

    @Before
    public void init() {
        context = new ChallengeEvaluationContext();
        Challenge challenge = new ChallengeBuilder().id(1L).point(22).user(new UserBuilder().id(2L).build()).build();
        context.setChallenge(challenge);
        context.setLanguage(ProgrammingLanguage.Python);

        user = new UserBuilder().id(3L).build();
        when(authenticationService.getCurrentUser()).thenReturn(user);
    }

    @Test
    public void shouldExecuteForOnlyAlgorithmChallenges() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(new ChallengeBuilder().challengeType(ChallengeType.ALGORITHM).buildWithRandomId());
        assertTrue(action.canExecute(context));
    }

    @Test
    public void shouldNotExecuteForNonAlgorithmChallenges() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(new ChallengeBuilder().challengeType(ChallengeType.CODE_KATA).buildWithRandomId());
        assertFalse(action.canExecute(context));
    }

    @Test
    public void shouldCreateSuccessResponseWhenInterpreterResultIsSuccess() {
        context.setInterpreterResult(InterpreterResult.createSuccessResult());

        when(userPointService.canUserWinPoint(context.getChallenge(), user, context.getLanguage())).thenReturn(true);
        when(messageService.getMessage("challenge.successWithPoint", 22)).thenReturn("success, 22 points");

        action.execute(context);

        assertTrue(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("success, 22 points"));
    }

    @Test
    public void shouldCreateFailedResponseWhenInterpreterResultIsFailed() {
        context.setInterpreterResult(InterpreterResult.createFailedResult());

        when(messageService.getMessage("challenge.failed")).thenReturn("failed");

        action.execute(context);

        assertFalse(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("failed"));
    }

    @Test
    public void shouldCreateSuccessfulWithoutPointWinningMessageIfUserHadAlreadyWonPoint() {
        context.setInterpreterResult(InterpreterResult.createSuccessResult());

        when(messageService.getMessage("challenge.success")).thenReturn("success");
        when(userPointService.canUserWinPoint(context.getChallenge(), user, context.getLanguage())).thenReturn(false);

        action.execute(context);

        assertTrue(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("success"));
    }

    @Test
    public void shouldCreateFailedResponseWhenInterpreterResultHasDescription() {
        InterpreterResult failedResult = InterpreterResult.createFailedResult();
        failedResult.setFailureType(InterpreterFailureType.SYNTAX_ERROR);
        context.setInterpreterResult(failedResult);

        when(messageService.getMessage("challenge.failed")).thenReturn("failed");
        when(messageService.getMessage("interpreter.syntaxError")).thenReturn("Syntax Error");

        action.execute(context);

        assertFalse(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("Syntax Error. failed"));
    }

}
