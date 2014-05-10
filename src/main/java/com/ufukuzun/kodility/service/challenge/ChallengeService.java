package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.ChallengeDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.service.language.SignatureGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeDao challengeDao;

    @Autowired
    private SignatureGeneratorService signatureGeneratorService;

    public List<Challenge> findAll() {
        List<Challenge> allChallenges = new ArrayList<>();
        Iterator<Challenge> iterator = challengeDao.findAll().iterator();
        while (iterator.hasNext()) {
            allChallenges.add(iterator.next());
        }

        return allChallenges;
    }

    public Challenge findById(String id) {
        return challengeDao.findById(id);
    }

    // TODO ufuk: complete - order by difficulty and find easiest challenge
    public Challenge findEasiestOne() {
        return challengeDao.findAll().iterator().next();
    }

    public Map<String, String> prepareSignaturesMapFor(Challenge challenge) {
        Map<String, String> signatures = new HashMap<>();
        for (ProgrammingLanguage language : ProgrammingLanguage.values()) {
            String signature = signatureGeneratorService.generatorSignatureFor(challenge, language);
            signatures.put(language.getShortName(), signature);
        }
        return signatures;
    }

    public String saveChallenge(Challenge challenge) {
        return challengeDao.save(challenge).getId();
    }

}
