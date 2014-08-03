package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.service.language.SignatureGeneratorService;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import com.ufukuzun.kodility.testutils.builder.ChallengeBuilder;
import com.ufukuzun.kodility.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChallengeServiceTest {

    @InjectMocks
    private ChallengeService service;

    @Mock
    private SignatureGeneratorService signatureGeneratorService;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    public void shouldPrepareChallengeSignatures() {
        User user = new UserBuilder().id(1L).build();
        Challenge challenge = new ChallengeBuilder().id(1L).user(user).build();

        when(signatureGeneratorService.generatorSignatureFor(challenge, ProgrammingLanguage.Python)).thenReturn("def solution():");
        when(signatureGeneratorService.generatorSignatureFor(challenge, ProgrammingLanguage.JavaScript)).thenReturn("function solution() {}");

        when(authenticationService.getCurrentUser()).thenReturn(user);

        Map<String, String> resultMap = service.prepareSignaturesMapFor(challenge);

        assertThat(resultMap.size(), equalTo(2));
        assertThat(resultMap.get(ProgrammingLanguage.Python.getShortName()), equalTo("def solution():"));
        assertThat(resultMap.get(ProgrammingLanguage.JavaScript.getShortName()), equalTo("function solution() {}"));
    }

}