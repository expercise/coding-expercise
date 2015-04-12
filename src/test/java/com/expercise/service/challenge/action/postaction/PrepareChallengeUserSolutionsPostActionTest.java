package com.expercise.service.challenge.action.postaction;

import com.expercise.controller.challenge.model.UserSolutionModel;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.Solution;
import com.expercise.domain.challenge.TestCase;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.InterpreterResult;
import com.expercise.interpreter.TestCaseWithResult;
import com.expercise.service.challenge.SolutionService;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.challenge.model.SolutionValidationResult;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.SolutionBuilder;
import com.expercise.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Locale;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrepareChallengeUserSolutionsPostActionTest {

    @InjectMocks
    private PrepareChallengeUserSolutionsPostAction action;

    @Mock
    private SolutionService solutionService;

    @Before
    public void before() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void shouldExecuteWhenInterpretationIsSuccessful() {
        TestCase testCase = new TestCase();
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        InterpreterResult successResult = InterpreterResult.createSuccessResult();
        context.setInterpreterResult(successResult);
        context.setChallenge(new ChallengeBuilder().id(1L).testCases(testCase).build());
        context.addTestCaseWithResult(new TestCaseWithResult(testCase));

        assertTrue(action.canExecute(context));
    }

    @Test
    public void shouldNotExecuteWhenInterpretationIsFailed() {
        TestCase testCase = new TestCase();
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(new ChallengeBuilder().id(2L).testCases(testCase).build());
        InterpreterResult failedResult = InterpreterResult.createFailedResult();
        context.setInterpreterResult(failedResult);
        context.addTestCaseWithResult(new TestCaseWithResult(testCase));

        assertFalse(action.canExecute(context));
    }

    @Test
    public void shouldAppendSolutionsOfTheChallengeForUserToTheSolutionResult() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        Challenge challenge = new ChallengeBuilder().buildWithRandomId();
        context.setChallenge(challenge);
        context.setSolutionValidationResult(SolutionValidationResult.createSuccessResult("success"));

        ArrayList<UserSolutionModel> userSolutionModels = new ArrayList<>();
        Solution solution1 = new SolutionBuilder().solution("my solution 1")
                .programmingLanguage(ProgrammingLanguage.Python).createDate(DateUtils.toDateTimeWithNamedMonth("01 October 2012 13:43")).build();
        Solution solution2 = new SolutionBuilder().solution("my solution 2")
                .programmingLanguage(ProgrammingLanguage.JavaScript).createDate(DateUtils.toDateTimeWithNamedMonth("14 October 2013 19:12")).build();
        userSolutionModels.add(UserSolutionModel.createFrom(solution1));
        userSolutionModels.add(UserSolutionModel.createFrom(solution2));

        when(solutionService.getUserSolutionModels(context.getChallenge())).thenReturn(userSolutionModels);

        action.execute(context);

        assertThat(context.getSolutionValidationResult().getUserSolutionModels().size(), equalTo(2));

        UserSolutionModel firstUserSolutionModel = context.getSolutionValidationResult().getUserSolutionModels().get(0);
        assertThat(firstUserSolutionModel.getProgrammingLanguage(), equalTo("Python"));
        assertThat(firstUserSolutionModel.getLanguageShortName(), equalTo("py"));
        assertThat(firstUserSolutionModel.getSolution(), equalTo("my solution 1"));
        assertThat(firstUserSolutionModel.getSolutionDate(), equalTo("01 October 2012 13:43"));

        UserSolutionModel secondUserSolutionModel = context.getSolutionValidationResult().getUserSolutionModels().get(1);
        assertThat(secondUserSolutionModel.getProgrammingLanguage(), equalTo("JavaScript"));
        assertThat(secondUserSolutionModel.getLanguageShortName(), equalTo("js"));
        assertThat(secondUserSolutionModel.getSolution(), equalTo("my solution 2"));
        assertThat(secondUserSolutionModel.getSolutionDate(), equalTo("14 October 2013 19:12"));
    }

}