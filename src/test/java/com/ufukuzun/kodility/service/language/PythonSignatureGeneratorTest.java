package com.ufukuzun.kodility.service.language;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.DataTypes;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PythonSignatureGeneratorTest {

    private PythonSignatureGenerator signatureGenerator = new PythonSignatureGenerator();

    @Test
    public void shouldGeneratePatternWithoutParameters() {
        Challenge challenge = new Challenge();

        assertThat(signatureGenerator.generate(challenge), equalTo("def solution():\n"));
    }

    @Test
    public void shouldGeneratePatternWithMultipleParameters() {
        Challenge challenge = new Challenge();

        List<String> inputTypes = new ArrayList<>();
        inputTypes.add(DataTypes.Integer.getClassName());
        inputTypes.add(DataTypes.Integer.getClassName());
        challenge.setInputTypes(inputTypes);
        challenge.setOutputType(DataTypes.Integer.getClassName());

        assertThat(signatureGenerator.generate(challenge), equalTo("def solution(a, b):\n"));
    }

}
