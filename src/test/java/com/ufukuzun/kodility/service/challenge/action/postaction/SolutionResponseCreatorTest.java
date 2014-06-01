package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.i18n.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SolutionResponseCreatorTest {

    @InjectMocks
    private SolutionResponseCreator action;

    @Mock
    private MessageService messageService;

    @Test
    public void shouldCreateSuccessResponseWhenInterpreterResultIsSuccess() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setInterpreterResult(InterpreterResult.createSuccessResult("success interpreter result"));

        when(messageService.getMessage("challenge.success")).thenReturn("success");

        action.execute(context);

        assertTrue(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("success"));
    }

    @Test
    public void shouldCreateFailedResponseWhenInterpreterResultIsFailed() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setInterpreterResult(InterpreterResult.createFailedResult("failed interpreter result"));

        when(messageService.getMessage("challenge.failed")).thenReturn("failed");

        action.execute(context);

        assertFalse(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("failed"));
    }

}
