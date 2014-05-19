package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.ChallengeDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.service.language.SignatureGeneratorService;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeDao challengeDao;

    @Autowired
    private SignatureGeneratorService signatureGeneratorService;

    @Autowired
    private AuthenticationService authenticationService;

    public List<Challenge> findAll() {
        return challengeDao.findAll();
    }

    public Challenge findById(Long id) {
        return challengeDao.findOne(id);
    }

    public Map<String, String> prepareSignaturesMapFor(Challenge challenge) {
        Map<String, String> signatures = new HashMap<>();
        for (ProgrammingLanguage language : ProgrammingLanguage.values()) {
            String signature = signatureGeneratorService.generatorSignatureFor(challenge, language);
            signatures.put(language.getShortName(), signature);
        }
        return signatures;
    }

    public Long saveChallenge(Challenge challenge) {
        if (challenge.isPersisted()) {
            challengeDao.update(challenge);
        } else {
            challenge.setUser(authenticationService.getCurrentUser());
            challengeDao.save(challenge);
        }
        return challenge.getId();
    }

}
