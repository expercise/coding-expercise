package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.ChallengeRepository;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.TestCase;
import com.ufukuzun.kodility.enums.Lingo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @PostConstruct  // TODO ufuk: remove after "Challenge Management" page
    public void init() {
//        if (challengeRepository.findAll().iterator().hasNext()) {
//            return;
//        }

//        challengeRepository.save(challenge);
    }

    public Challenge findById(String id) {
        return getSampleChallenge();
    }

    private Challenge getSampleChallenge() {
        Challenge challenge = new Challenge();

        challenge.setId("123123");

        HashMap<Lingo, String> descriptions = new HashMap<Lingo, String>();
        descriptions.put(Lingo.English, "\"a\" and \"b\" are integer numbers. Write a function that sums \"a\" and \"b\", and returns.");
        descriptions.put(Lingo.Turkish, "\"a\" ve \"b\" iki tamsayıdır. \"a\" ve \"b\" tamsayılarını toplayan ve sonucu dönen bir fonksiyon yazınız.");
        challenge.setDescriptions(descriptions);

        TestCase testCase = new TestCase();
        List<Object> inputValues = new ArrayList<Object>();
        inputValues.add(12);
        inputValues.add(23);
        testCase.setInputs(inputValues);
        testCase.setOutput(35);

        challenge.addTestCase(testCase);

        List<Class> inputTypes = new ArrayList<Class>();
        inputTypes.add(Integer.class);
        inputTypes.add(Integer.class);
        challenge.setInputTypes(inputTypes);
        challenge.setOutputType(Integer.class);

        return challenge;
    }

    // TODO ufuk: complete - order by difficulty and find easiest challenge
    public Challenge findEasiestOne() {
        return challengeRepository.findAll().iterator().next();
    }

}
