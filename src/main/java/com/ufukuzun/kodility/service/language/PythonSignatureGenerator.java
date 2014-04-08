package com.ufukuzun.kodility.service.language;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PythonSignatureGenerator implements SignatureGenerator {

    private final static String SIGNATURE_PATTERN = "def solution({params}):\n";
    private final static String[] letters = new String[] {"a", "b", "c", "d"};

    @Override
    public String generate(Challenge challenge) {
        return generateFunctionCallSignature(challenge);
    }

    private String generateFunctionCallSignature(Challenge challenge) {
        List<String> params = new ArrayList<String>();

        for (int i = 0; i < challenge.getInputTypes().size(); i++) {
            params.add(letters[i]);
        }

        return SIGNATURE_PATTERN.replace("{params}", StringUtils.join(params, ", "));
    }

}
