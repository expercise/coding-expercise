package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.controller.challenge.model.SolutionFromUser;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.service.challenge.action.PostEvaluationExecutor;
import com.ufukuzun.kodility.service.challenge.action.PreEvaluationExecutor;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.challenge.model.SolutionValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.beans.Introspector;

@Service
public class SolutionValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SolutionValidationService.class);

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PreEvaluationExecutor preEvaluationExecutor;

    @Autowired
    private PostEvaluationExecutor postEvaluationExecutor;

    @Autowired
    private ApplicationContext applicationContext;

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
        Interpreter interpreter = findInterpreterFor(context.getLanguage());
        interpreter.interpret(context);
    }

    private Interpreter findInterpreterFor(ProgrammingLanguage programmingLanguage) {
        try {
            String interpreterBeanName = Introspector.decapitalize(programmingLanguage.name()) + "Interpreter";
            return (Interpreter) applicationContext.getBean(interpreterBeanName);
        } catch (BeansException e) {
            LOGGER.error("Unsupported programming language: {}", programmingLanguage);
            throw new IllegalArgumentException("Unsupported programming language: " + programmingLanguage);
        }
    }

}
