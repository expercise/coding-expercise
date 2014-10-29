package com.ufukuzun.kodility.service.language;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SignatureGeneratorService {

    @Autowired
    private ApplicationContext applicationContext;

    public String generatorSignatureFor(Challenge challenge, ProgrammingLanguage language) {
        return findSignatureGeneratorFor(language).generate(challenge);
    }

    private SignatureGenerator findSignatureGeneratorFor(ProgrammingLanguage language) {
        return applicationContext.getBeansOfType(SignatureGenerator.class)
                .values().stream()
                .filter(g -> g.canGenerateFor(language))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported programming language: " + language));
    }

}
