package com.ufukuzun.kodility.controller.challenge;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.enums.Lingo;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// TODO ufuk: this class is for tests, remove after database configuration
public class SampleChallenges {

    public static List<Challenge> sampleChallenges = new ArrayList<Challenge>();

    static {
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

        sampleChallenges.add(challenge);
    }

}
