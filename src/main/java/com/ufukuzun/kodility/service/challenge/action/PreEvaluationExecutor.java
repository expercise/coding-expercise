package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreEvaluationExecutor {

    @Autowired(required = false)
    private List<PreEvaluationAction> preEvaluationActions = new ArrayList<>();

    public void execute(ChallengeEvaluationContext context) {
        preEvaluationActions.forEach(a -> a.execute(context));
    }

}
