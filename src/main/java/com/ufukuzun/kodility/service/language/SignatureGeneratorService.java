package com.ufukuzun.kodility.service.language;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SignatureGeneratorService {

    @Autowired(required = false)
    private List<SignatureGenerator> signatureGenerators = new ArrayList<>();

    public String generatorSignatureFor(Challenge challenge, ProgrammingLanguage language) {
        return findSignatureGeneratorFor(language).generate(challenge);
    }

    private SignatureGenerator findSignatureGeneratorFor(ProgrammingLanguage language) {
        return signatureGenerators.stream()
                .filter(g -> g.canGenerateFor(language))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported programming language: " + language));
    }

}
