package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PostEvaluationExecutor implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void execute(ChallengeEvaluationContext context) {
        Map<String, PostEvaluationAction> actions = applicationContext.getBeansOfType(PostEvaluationAction.class);
        for (Map.Entry<String, PostEvaluationAction> action : actions.entrySet()) {
            action.getValue().execute(context);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
