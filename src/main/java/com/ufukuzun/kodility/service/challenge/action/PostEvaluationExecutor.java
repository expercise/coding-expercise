package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.util.PrioritySorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PostEvaluationExecutor {

    @Autowired
    private ApplicationContext applicationContext;

    public void execute(ChallengeEvaluationContext context) {
        applicationContext
                .getBeansOfType(PostEvaluationAction.class).values().stream()
                .filter(action -> action.canExecute(context))
                .sorted(new PrioritySorter())
                .forEach(a -> a.execute(context));
    }

}
