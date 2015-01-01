package com.kodility.service.language;

import com.kodility.domain.challenge.Challenge;
import com.kodility.enums.ProgrammingLanguage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JavaScriptSignatureGenerator implements SignatureGenerator {

    private static final String SIGNATURE_PATTERN = "function solution({params}) {\n\t\n}";

    @Override
    public boolean canGenerateFor(ProgrammingLanguage language) {
        return ProgrammingLanguage.JavaScript == language;
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
