package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PreEvaluationExecutor {

    @Autowired
    private ApplicationContext applicationContext;

    public void execute(ChallengeEvaluationContext context) {
        applicationContext
                .getBeansOfType(PreEvaluationAction.class)
                .values().stream()
                .forEach(a -> a.execute(context));
    }

}
