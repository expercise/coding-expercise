package com.kodility.service.language;

import com.kodility.domain.challenge.Challenge;
import com.kodility.enums.ProgrammingLanguage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PythonSignatureGenerator implements SignatureGenerator {

    private static final String SIGNATURE_PATTERN = "def solution({params}):\n\t";

    @Override
    public boolean canGenerateFor(ProgrammingLanguage language) {
        return ProgrammingLanguage.Python == language;
    }

    @Override
    public String generate(Challenge challenge) {
        return generateFunctionCallSignature(challenge);
    }

    private String generateFunctionCallSignature(Challenge challenge) {
        List<String> params = new ArrayList<>();

        for (int i = 0; i < challenge.getInputTypes().size(); i++) {
            params.add(LETTERS[i]);
        }

        return SIGNATURE_PATTERN.replace("{params}", String.join(", ", params));
    }

}
