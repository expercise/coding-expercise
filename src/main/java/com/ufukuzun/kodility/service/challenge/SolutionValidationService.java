package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.controller.challenge.model.SolutionFromUser;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.challenge.model.SolutionValidationResult;
import com.ufukuzun.kodility.service.i18n.MessageService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SolutionValidationService implements ApplicationContextAware {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private MessageService messageService;

    private ApplicationContext applicationContext;

    public SolutionValidationResult validateSolution(SolutionFromUser solutionFromUser) {
        Challenge challenge = challengeService.findById(solutionFromUser.getChallengeId());
        Interpreter interpreter = findInterpreterFor(solutionFromUser.getProgrammingLanguage());

        InterpreterResult resultForUser = interpreter.interpret(solutionFromUser.getSolution(), challenge);
        if (resultForUser.isSuccess()) {
            return SolutionValidationResult.createSuccessResult(messageService.getMessage("challenge.success"));
        }
        return SolutionValidationResult.createFailedResult(messageService.getMessage("challenge.failed"));
    }

    private Interpreter findInterpreterFor(ProgrammingLanguage programmingLanguage) {
        Map<String, Interpreter> interpreters = applicationContext.getBeansOfType(Interpreter.class);
        for (Interpreter interpreter : interpreters.values()) {
            if (interpreter.canInterpret(programmingLanguage)) {
                return interpreter;
            }
        }

        throw new IllegalArgumentException("There is no such programming language as this.");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
