package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.ChallengeDao;
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
    private ChallengeDao challengeDao;

    @PostConstruct  // TODO ufuk: remove after "Challenge Management" page
    public void init() {
        if (challengeDao.findAll().iterator().hasNext()) {
            return;
        }

        challengeDao.save(getSampleChallenge());
    }

    public Challenge findById(String id) {
        return challengeDao.findById(id);
    }

    // TODO ufuk: complete - order by difficulty and find easiest challenge
    public Challenge findEasiestOne() {
        return challengeDao.findAll().iterator().next();
    }

    private Challenge getSampleChallenge() {
        Challenge challenge = new Challenge();

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

        List<String> inputTypes = new ArrayList<String>();
        inputTypes.add(Integer.class.getName());
        inputTypes.add(Integer.class.getName());
        challenge.setInputTypes(inputTypes);
        challenge.setOutputType(Integer.class.getName());

        return challenge;
    }

}
