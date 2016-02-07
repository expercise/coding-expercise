package com.expercise.service.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.TestCaseWithResult;
import com.expercise.interpreter.TestCasesWithSourceCacheModel;
import com.expercise.service.user.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class UserTestCaseStateService {

    private final Map<String, TestCasesWithSourceCacheModel> userStateCacheMap = new ConcurrentHashMap<>();

    @Autowired
    private AuthenticationService authenticationService;

    public TestCasesWithSourceCacheModel getUserTestCasesOf(Challenge challenge, ProgrammingLanguage programmingLanguage) {
        String userCacheKey = getCacheKeyFrom(challenge, programmingLanguage);
        TestCasesWithSourceCacheModel modelFromCache = userStateCacheMap.get(userCacheKey);
        if (modelFromCache != null) {
            return modelFromCache;
        }
        TestCasesWithSourceCacheModel newCacheModel = new TestCasesWithSourceCacheModel(
                getTestCaseWithResults(challenge)
        );
        userStateCacheMap.put(userCacheKey, newCacheModel);
        return newCacheModel;
    }

    private List<TestCaseWithResult> getTestCaseWithResults(Challenge challenge) {
        List<TestCaseWithResult> newTestCaseWithResults = new ArrayList<>();
        if (challenge.isCodeKata()) {
            TestCase firstTestCaseOfChallenge = challenge.getTestCases().get(0);
            newTestCaseWithResults.add(new TestCaseWithResult(firstTestCaseOfChallenge));
        } else {
            newTestCaseWithResults.addAll(challenge.getTestCases().stream().map(TestCaseWithResult::new).collect(Collectors.toList()));
        }
        return newTestCaseWithResults;
    }

    public void saveNextTestCase(Challenge challenge, String currentSourceCode, ProgrammingLanguage programmingLanguage) {
        TestCasesWithSourceCacheModel cacheModel = getUserTestCasesOf(challenge, programmingLanguage);
        cacheModel.setCurrentSourceCode(currentSourceCode);

        List<TestCaseWithResult> currentTestCasesStateOfUser = cacheModel.getTestCaseResults();
        TestCase nextTestCase = challenge.getTestCases().get(currentTestCasesStateOfUser.size());
        TestCaseWithResult nextTestCaseWithResult = new TestCaseWithResult(nextTestCase);
        currentTestCasesStateOfUser.add(nextTestCaseWithResult);
    }

    public void saveUserState(Challenge challenge, String currentSourceCode, ProgrammingLanguage programmingLanguage, List<TestCaseWithResult> testCaseWithResults) {
        TestCasesWithSourceCacheModel userCacheModel = getUserTestCasesOf(challenge, programmingLanguage);
        userCacheModel.setCurrentSourceCode(currentSourceCode);
        userCacheModel.setTestCaseResults(testCaseWithResults);
    }

    public void resetUserState(Challenge challenge, ProgrammingLanguage programmingLanguage) {
        TestCasesWithSourceCacheModel userTestCasesOf = getUserTestCasesOf(challenge, programmingLanguage);
        if (challenge.isCodeKata()) {
            userTestCasesOf.clear();
            saveNextTestCase(challenge, StringUtils.EMPTY, programmingLanguage);
        } else {
            userTestCasesOf.reset();
        }
    }

    private String getCacheKeyFrom(Challenge challenge, ProgrammingLanguage programmingLanguage) {
        User user = authenticationService.getCurrentUser();
        return challenge.getId() + "_" + programmingLanguage.name() + "_" + user.getId();
    }

}
