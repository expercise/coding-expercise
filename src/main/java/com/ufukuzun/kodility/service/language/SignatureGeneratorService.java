package com.ufukuzun.kodility.service.language;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SignatureGeneratorService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public String generatorSignatureFor(Challenge challenge, ProgrammingLanguage language) {
        return findSignatureGeneratorFor(language).generate(challenge);
    }

    private SignatureGenerator findSignatureGeneratorFor(ProgrammingLanguage language) {
        Map<String, SignatureGenerator> signatureGenerators = applicationContext.getBeansOfType(SignatureGenerator.class);
        for (SignatureGenerator signatureGenerator : signatureGenerators.values()) {
            if (signatureGenerator.canGenerateFor(language)) {
                return signatureGenerator;
            }
        }

        throw new IllegalArgumentException("Unsupported programming language: " + language);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
