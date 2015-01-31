package com.expercise.service.language;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.ChallengeInputType;
import com.expercise.enums.DataType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PythonSignatureGeneratorTest {

    @InjectMocks
    private PythonSignatureGenerator signatureGenerator;

    @Test
    public void shouldGeneratePatternWithoutParameters() {
        Challenge challenge = new Challenge();

        assertThat(signatureGenerator.generate(challenge), equalTo("def solution():\n\t"));
    }

    @Test
    public void shouldGeneratePatternWithMultipleParameters() {
        Challenge challenge = new Challenge();

        List<DataType> inputTypes = new ArrayList<>();
        inputTypes.add(DataType.Integer);
        inputTypes.add(DataType.Integer);
        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));
        challenge.setOutputType(DataType.Integer);

        assertThat(signatureGenerator.generate(challenge), equalTo("def solution(a, b):\n\t"));
    }

}
