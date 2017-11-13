package com.expercise.service.language;

import com.expercise.domain.challenge.Challenge;
import com.expercise.enums.ProgrammingLanguage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Python2SignatureGenerator implements SignatureGenerator {

    private static final String SIGNATURE_PATTERN = "def solution({params}):\n\t";

    @Override
    public boolean canGenerateFor(ProgrammingLanguage language) {
        return ProgrammingLanguage.Python2 == language;
    }

    @Override
    public String generate(Challenge challenge) {
        return generateFunctionCallSignature(challenge);
    }

    private String generateFunctionCallSignature(Challenge challenge) {
        List<String> params = new ArrayList<>();

        params.addAll(Arrays.asList(LETTERS).subList(0, challenge.getInputTypes().size()));

        return SIGNATURE_PATTERN.replace("{params}", String.join(", ", params));
    }

}
