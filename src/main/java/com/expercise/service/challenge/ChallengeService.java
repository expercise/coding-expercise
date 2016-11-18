package com.expercise.service.challenge;

import com.expercise.controller.challenge.model.ChallengeModel;
import com.expercise.dao.challenge.ChallengeDao;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.TestCasesWithSourceCacheModel;
import com.expercise.interpreter.TestCasesWithSourceModel;
import com.expercise.service.configuration.ConfigurationService;
import com.expercise.service.language.SignatureGeneratorService;
import com.expercise.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private ChallengeModelHelper challengeModelHelper;

    @Autowired
    private UserTestCaseStateService userTestCaseStateService;

    @Autowired
    private ConfigurationService configurationService;

    @Value("${coding-expercise.challenge-approval-strategy}")
    private String challengeApprovalStrategy;

    public List<Challenge> findAllChallengesOfUser() {
        return challengeDao.findAllByUser(authenticationService.getCurrentUser());
    }

    public List<Challenge> findAllApprovedChallengesOfUser(User user) {
        return challengeDao.findAllApprovedByUser(user);
    }

    public List<Challenge> findAll() {
        return challengeDao.findAll();
    }

    public Challenge findById(Long id) {
        return challengeDao.findOne(id);
    }

    public List<Challenge> findNotThemedApprovedChallenges() {
        return challengeDao.findNotThemedApprovedChallenges();
    }

    public Map<String, String> prepareSignaturesMapFor(Challenge challenge) {
        Map<String, String> signaturesMap = new HashMap<>();
        for (ProgrammingLanguage language : ProgrammingLanguage.values()) {
            String signature = signatureGeneratorService.generatorSignatureFor(challenge, language);
            signaturesMap.put(language.getShortName(), signature);
        }

        return signaturesMap;
    }

    public Long saveChallenge(ChallengeModel challengeModel) {
        Challenge challenge = prepareChallengeForSaving(challengeModel);

        if (challenge.isPersisted()) {
            challengeDao.update(challenge);
        } else {
            if ("auto".equalsIgnoreCase(challengeApprovalStrategy)) {
                challenge.setApproved(true);
            }
            challenge.setUser(authenticationService.getCurrentUser());
            challengeDao.save(challenge);
        }

        return challenge.getId();
    }

    private Challenge prepareChallengeForSaving(ChallengeModel challengeModel) {
        Challenge challenge;
        if (challengeModel.getChallengeId() != null) {
            challenge = challengeDao.findOne(challengeModel.getChallengeId());
            challengeModelHelper.mergeChallengeWithModel(challengeModel, challenge);
        } else {
            challenge = challengeModelHelper.createChallengeFrom(challengeModel);
        }
        return challenge;
    }

    public TestCasesWithSourceModel getUserStateFor(Challenge challenge, ProgrammingLanguage programmingLanguage) {
        TestCasesWithSourceCacheModel cacheStateOfUser = userTestCaseStateService.getUserTestCasesOf(challenge, programmingLanguage);
        return new TestCasesWithSourceModel(cacheStateOfUser);
    }

    public TestCasesWithSourceModel resetUserStateFor(Challenge challenge, ProgrammingLanguage programmingLanguage) {
        userTestCaseStateService.resetUserState(challenge, programmingLanguage);
        return getUserStateFor(challenge, programmingLanguage);
    }

    public Challenge getDefaultChallenge() {
        return configurationService.getIdOfDefaultChallenge()
                .map(this::findById)
                .orElseGet(() -> null);
    }

}
