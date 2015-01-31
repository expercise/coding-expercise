package com.expercise.service.challenge.action;

import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PreEvaluationExecutorTest {

    @InjectMocks
    private PreEvaluationExecutor executor;

    @Test
    public void shouldExecutePreActions() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();

        PreEvaluationAction mockPreAction1 = mock(PreEvaluationAction.class);
        PreEvaluationAction mockPreAction2 = mock(PreEvaluationAction.class);

        ReflectionTestUtils.setField(executor, "preEvaluationActions", Arrays.asList(mockPreAction1, mockPreAction2));

        executor.execute(context);

        verify(mockPreAction1).execute(context);
        verify(mockPreAction2).execute(context);
    }

}
