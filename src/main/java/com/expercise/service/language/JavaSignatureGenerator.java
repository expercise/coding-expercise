package com.expercise.service.language;

import com.expercise.domain.challenge.Challenge;
import com.expercise.enums.DataType;
import com.expercise.enums.ProgrammingLanguage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JavaSignatureGenerator implements SignatureGenerator {

    private static final String SIGNATURE_PATTERN = "public class Solution {\n\n\tpublic {returnType} solution({params}) {\n\t\t\n\t}\n\n}";

    private static final Map<DataType, Class<?>> TYPE_MAP = new HashMap<>();

    static {
        TYPE_MAP.put(DataType.Integer, Integer.class);
        TYPE_MAP.put(DataType.Text, String.class);
        TYPE_MAP.put(DataType.Array, List.class);
    }

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
            Class javaClassOfInputType = getJavaClassOf(challenge.getInputTypes().get(i).getInputType());
            params.add(javaClassOfInputType.getSimpleName() + " " + LETTERS[i]);
        }

        return SIGNATURE_PATTERN
                .replace("{params}", String.join(", ", params))
                .replace("{returnType}", getJavaClassOf(challenge.getOutputType()).getSimpleName());
    }

    private Class getJavaClassOf(DataType dataType) {
        return TYPE_MAP.get(dataType);
    }

}
