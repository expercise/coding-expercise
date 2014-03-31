package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.ChallengeRepository;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.enums.Lingo;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @PostConstruct  // TODO ufuk: remove after "Challenge Management" page
    public void init() {
        if (challengeRepository.findAll().iterator().hasNext()) {
            return;
        }

        Challenge challenge = new Challenge();

        HashMap<Lingo, String> descriptions = new HashMap<Lingo, String>();
        descriptions.put(Lingo.English, "\"a\" and \"b\" are integer numbers. Write a function that sums \"a\" and \"b\", and returns.");
        descriptions.put(Lingo.Turkish, "\"a\" ve \"b\" iki tamsayıdır. \"a\" ve \"b\" tamsayılarını toplayan ve sonucu dönen bir fonksiyon yazınız.");
        challenge.setDescriptions(descriptions);

        challenge.setInputs(Arrays.asList(
                Arrays.asList("1", "2"),
                Arrays.asList("3", "4"),
                Arrays.asList("250", "-5")
        ));

        Solution solution = new Solution();
        solution.setSolution("function solution(a, b) {return a + b;}");
        solution.setSolutionTemplate("function solution(a, b) {\n}");
        solution.setProgrammingLanguage(ProgrammingLanguage.JavaScript);
        solution.setCallPattern("solution(%s, %s);");
        challenge.setSolutions(Arrays.asList(solution));

        challengeRepository.save(challenge);
    }

    public Challenge findById(String id) {
        return challengeRepository.findById(id);
    }

    // TODO ufuk: complete - order by difficulty and find easiest challenge
    public Challenge findEasiestOne() {
        return challengeRepository.findAll().iterator().next();
    }

}
