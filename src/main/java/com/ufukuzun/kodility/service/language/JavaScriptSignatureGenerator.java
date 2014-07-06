package com.ufukuzun.kodility.service.language;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class JavaScriptSignatureGenerator implements SignatureGenerator {

    private static final String SIGNATURE_PATTERN = "function solution({params}) {\n\n}";

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

        for (int i = 0; i < challenge.getInputTypes().size(); i++) {
            params.add(LETTERS[i]);
        }

        return SIGNATURE_PATTERN.replace("{params}", StringUtils.join(params, ", "));
    }

}
