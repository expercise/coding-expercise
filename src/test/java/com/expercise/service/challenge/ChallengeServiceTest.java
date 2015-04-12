package com.expercise.service.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.challenge.TestCaseInputValue;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.TestCaseModel;
import com.expercise.interpreter.TestCaseWithResult;
import com.expercise.interpreter.TestCasesWithSourceCacheModel;
import com.expercise.interpreter.TestCasesWithSourceModel;
import com.expercise.service.language.SignatureGeneratorService;
import com.expercise.service.user.AuthenticationService;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.TestCaseBuilder;
import com.expercise.testutils.builder.TestCaseInputValueBuilder;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChallengeServiceTest {

    @InjectMocks
    private ChallengeService service;

    @Mock
    private SignatureGeneratorService signatureGeneratorService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserTestCaseStateService userTestCaseStateService;

    @Test
    public void shouldPrepareChallengeSignatures() {
        User user = new UserBuilder().id(1L).build();
        Challenge challenge = new ChallengeBuilder().id(1L).user(user).build();

        when(signatureGeneratorService.generatorSignatureFor(challenge, ProgrammingLanguage.Python)).thenReturn("def solution():");
        when(signatureGeneratorService.generatorSignatureFor(challenge, ProgrammingLanguage.JavaScript)).thenReturn("function solution() {}");
        when(signatureGeneratorService.generatorSignatureFor(challenge, ProgrammingLanguage.Java)).thenReturn("public class Solution { public Integer solution() {} }");

        when(authenticationService.getCurrentUser()).thenReturn(user);

        Map<String, String> resultMap = service.prepareSignaturesMapFor(challenge);

        assertThat(resultMap.size(), equalTo(3));
        assertThat(resultMap.get(ProgrammingLanguage.Python.getShortName()), equalTo("def solution():"));
        assertThat(resultMap.get(ProgrammingLanguage.JavaScript.getShortName()), equalTo("function solution() {}"));
        assertThat(resultMap.get(ProgrammingLanguage.Java.getShortName()), equalTo("public class Solution { public Integer solution() {} }"));
    }

    @Test
    public void shouldGetUserStateFromCache() {
        Challenge challenge = new ChallengeBuilder().id(1L).build();

        TestCaseInputValue inputValue1 = new TestCaseInputValueBuilder().id(1L).inputValue("i1").build();
        TestCaseInputValue inputValue2 = new TestCaseInputValueBuilder().id(2L).inputValue("i2").build();
        TestCase testCase = new TestCaseBuilder().id(1L).challenge(challenge).inputs(inputValue1, inputValue2).output("o1").build();

        challenge.addTestCase(testCase);

        List<TestCaseWithResult> testCasesWithResults = new ArrayList<>();
        testCasesWithResults.add(new TestCaseWithResult(testCase));
        TestCasesWithSourceCacheModel cacheModel = new TestCasesWithSourceCacheModel("sourceCode", testCasesWithResults);

        when(userTestCaseStateService.getUserTestCasesOf(challenge, ProgrammingLanguage.Java)).thenReturn(cacheModel);

        TestCasesWithSourceModel userState = service.getUserStateFor(challenge, ProgrammingLanguage.Java);

        assertThat(userState.getTestCaseModels().size(), equalTo(1));
        TestCaseModel model = userState.getTestCaseModels().get(0);
        assertThat(model.getInputs().get(0), equalTo("i1"));
        assertThat(model.getInputs().get(1), equalTo("i2"));
        assertThat(model.getOutput(), equalTo("o1"));
    }

    @Test
    public void shouldGetUserNewStateFromCacheAfterReset() {
        Challenge challenge = new ChallengeBuilder().id(1L).build();

        TestCaseInputValue inputValue1 = new TestCaseInputValueBuilder().id(1L).inputValue("i1").build();
        TestCaseInputValue inputValue2 = new TestCaseInputValueBuilder().id(2L).inputValue("i2").build();
        TestCase testCase = new TestCaseBuilder().id(1L).challenge(challenge).inputs(inputValue1, inputValue2).output("o1").build();

        challenge.addTestCase(testCase);

        List<TestCaseWithResult> testCasesWithResults = new ArrayList<>();
        testCasesWithResults.add(new TestCaseWithResult(testCase));
        TestCasesWithSourceCacheModel cacheModel = new TestCasesWithSourceCacheModel("sourceCode", testCasesWithResults);

        when(userTestCaseStateService.getUserTestCasesOf(challenge, ProgrammingLanguage.Java)).thenReturn(cacheModel);

        TestCasesWithSourceModel userState = service.resetUserStateFor(challenge, ProgrammingLanguage.Java);

        verify(userTestCaseStateService).resetUserState(challenge, ProgrammingLanguage.Java);

        assertThat(userState.getTestCaseModels().size(), equalTo(1));
        TestCaseModel model = userState.getTestCaseModels().get(0);
        assertThat(model.getInputs().get(0), equalTo("i1"));
        assertThat(model.getInputs().get(1), equalTo("i2"));
        assertThat(model.getOutput(), equalTo("o1"));
    }


}