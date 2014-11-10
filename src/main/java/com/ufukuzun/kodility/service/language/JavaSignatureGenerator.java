package com.ufukuzun.kodility.service.language;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.java.JavaInterpreter;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class JavaSignatureGenerator implements SignatureGenerator {

    private static final String SIGNATURE_PATTERN = "public class Solution {\n\n\tpublic {returnType} solution({params}) {\n\t\t\n\t}\n\n}";

    @Override
    public boolean canGenerateFor(ProgrammingLanguage language) {
        return ProgrammingLanguage.Java == language;
    }

    @Override
    public String generate(Challenge challenge) {
        return generateFunctionCallSignature(challenge);
    }

    private String generateFunctionCallSignature(Challenge challenge) {
        List<String> params = new ArrayList<>();

        for (int i = 0; i < challenge.getInputTypes().size(); i++) {
            Class javaClassOfInputType = JavaInterpreter.getJavaClassOf(challenge.getInputTypes().get(i).getInputType());
            params.add(javaClassOfInputType.getSimpleName() + " " + LETTERS[i]);
        }

        return SIGNATURE_PATTERN
                .replace("{params}", StringUtils.join(params, ", "))
                .replace("{returnType}", JavaInterpreter.getJavaClassOf(challenge.getOutputType()).getSimpleName());
    }

}
