package com.expercise.service.challenge;

import com.expercise.controller.challenge.model.SolutionFromUser;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.ChallengeType;
import com.expercise.domain.challenge.TestCase;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.Interpreter;
import com.expercise.interpreter.TestCaseWithResult;
import com.expercise.service.challenge.action.PostEvaluationExecutor;
import com.expercise.service.challenge.action.PreEvaluationExecutor;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.challenge.model.SolutionValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.beans.Introspector;
import java.util.List;

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

    @Autowired
    private UserTestCaseStateService userTestCaseStateService;

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
        if (challenge.getChallengeType() == ChallengeType.CODE_KATA) {
            List<TestCaseWithResult> testCaseResults = userTestCaseStateService.getUserTestCasesOf(challenge, solutionFromUser.getProgrammingLanguage()).getTestCaseResults();
            challengeEvaluationContext.getTestCaseWithResults().addAll(testCaseResults);
        }
        if (challenge.getChallengeType() == ChallengeType.ALGORITHM) {
            for (TestCase testCase : challenge.getTestCases()) {
                challengeEvaluationContext.addTestCaseWithResult(new TestCaseWithResult(testCase));
            }
        }
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
