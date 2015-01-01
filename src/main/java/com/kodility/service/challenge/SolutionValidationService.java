package com.kodility.service.challenge;

import com.kodility.controller.challenge.model.SolutionFromUser;
import com.kodility.domain.challenge.Challenge;
import com.kodility.enums.ProgrammingLanguage;
import com.kodility.interpreter.Interpreter;
import com.kodility.service.challenge.action.PostEvaluationExecutor;
import com.kodility.service.challenge.action.PreEvaluationExecutor;
import com.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.kodility.service.challenge.model.SolutionValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.beans.Introspector;

@Service
public class SolutionValidationService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PreEvaluationExecutor preEvaluationExecutor;

    @Autowired
    private PostEvaluationExecutor postEvaluationExecutor;

    public SolutionValidationResult validateSolution(SolutionFromUser solutionFromUser) {
        ChallengeEvaluationContext context = createEvaluationContextFrom(solutionFromUser);

        preEvaluationExecutor.execute(context);
        interpret(context);
        postEvaluationExecutor.execute(context);

        return context.getSolutionValidationResult();
    }

    private ChallengeEvaluationContext createEvaluationContextFrom(SolutionFromUser solutionFromUser) {
        Challenge challenge = challengeService.findById(solutionFromUser.getChallengeId());
        ChallengeEvaluationContext challengeEvaluationContext = new ChallengeEvaluationContext();
        challengeEvaluationContext.setChallenge(challenge);
        challengeEvaluationContext.setSource(solutionFromUser.getSolution());
        challengeEvaluationContext.setLanguage(solutionFromUser.getProgrammingLanguage());
        return challengeEvaluationContext;
    }

    private void interpret(ChallengeEvaluationContext context) {
        findInterpreterFor(context.getLanguage()).interpret(context);
    }

    // TODO ufuk & batu: allow only JavaScript until sandboxing completed for others
    @SuppressWarnings("UnusedParameters")
    private Interpreter findInterpreterFor(ProgrammingLanguage programmingLanguage) {
        // String interpreterBeanName = Introspector.decapitalize(programmingLanguage.name()) + "Interpreter";
        String interpreterBeanName = Introspector.decapitalize(ProgrammingLanguage.JavaScript.name()) + "Interpreter";
        return (Interpreter) applicationContext.getBean(interpreterBeanName);
    }

}
