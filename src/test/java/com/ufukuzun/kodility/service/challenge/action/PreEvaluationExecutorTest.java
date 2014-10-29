package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PreEvaluationExecutorTest {

    @InjectMocks
    private PreEvaluationExecutor executor;

    @Mock
    private ApplicationContext applicationContext;

    @Test
    public void shouldExecutePreActions() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();

        final PreEvaluationAction mockPreAction1 = mock(PreEvaluationAction.class);
        final PreEvaluationAction mockPreAction2 = mock(PreEvaluationAction.class);

        Map<String, PreEvaluationAction> preActionMap = new HashMap<String, PreEvaluationAction>() {{
            put("mockPreAction1", mockPreAction1);
            put("mockPreAction2", mockPreAction2);
        }};

        when(applicationContext.getBeansOfType(PreEvaluationAction.class)).thenReturn(preActionMap);

        executor.execute(context);

        verify(mockPreAction1).execute(context);
        verify(mockPreAction2).execute(context);
    }

}
