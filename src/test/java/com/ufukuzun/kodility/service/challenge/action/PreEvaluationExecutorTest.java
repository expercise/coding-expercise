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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PreEvaluationExecutorTest {

    @InjectMocks
    private PreEvaluationExecutor executor;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private MockPreAction1 mockPreAction1;

    @Mock
    private MockPreAction2 mockPreAction2;

    @Test
    public void shouldExecutePreActions() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();

        Map<String, PreEvaluationAction> preActionMap = new HashMap<String, PreEvaluationAction>() {{
            put("mockPreAction1", mockPreAction1);
            put("mockPreAction2", mockPreAction2);
        }};

        when(applicationContext.getBeansOfType(PreEvaluationAction.class)).thenReturn(preActionMap);

        executor.execute(context);

        verify(mockPreAction1).execute(context);
        verify(mockPreAction2).execute(context);
    }

    private class MockPreAction1 implements PreEvaluationAction {
        @Override
        public void execute(ChallengeEvaluationContext context) {}
    }

    private class MockPreAction2 implements PreEvaluationAction {
        @Override
        public void execute(ChallengeEvaluationContext context) {}
    }

}
