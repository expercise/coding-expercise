package com.expercise.service.language;

import com.expercise.domain.challenge.Challenge;
import com.expercise.enums.Lingo;
import com.expercise.enums.ProgrammingLanguage;
import org.apache.commons.lang3.StringUtils;
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
        String customSignature = Lingo.getValueFrom(challenge.getSignatures());
        if (StringUtils.isNotBlank(customSignature)) {
            return customSignature;
        }
        return generateFunctionCallSignature(challenge);
    }

    private String generateFunctionCallSignature(Challenge challenge) {
        List<String> params = new ArrayList<>();

        params.addAll(Arrays.asList(LETTERS).subList(0, challenge.getInputTypes().size()));

        return SIGNATURE_PATTERN.replace("{params}", String.join(", ", params));
    }

}
