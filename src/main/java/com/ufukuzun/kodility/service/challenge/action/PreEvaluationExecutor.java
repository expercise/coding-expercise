package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PreEvaluationExecutor implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void execute(ChallengeEvaluationContext context) {
        Map<String, PreEvaluationAction> actions = applicationContext.getBeansOfType(PreEvaluationAction.class);
        for (Map.Entry<String, PreEvaluationAction> action : actions.entrySet()) {
            action.getValue().execute(context);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
