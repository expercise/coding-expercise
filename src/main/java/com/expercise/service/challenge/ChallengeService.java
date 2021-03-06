package com.expercise.service.challenge;

import com.expercise.controller.challenge.model.ChallengeModel;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.TestCasesWithSourceCacheModel;
import com.expercise.interpreter.TestCasesWithSourceModel;
import com.expercise.repository.challenge.ChallengeRepository;
import com.expercise.service.configuration.Configurations;
import com.expercise.service.language.SignatureGeneratorService;
import com.expercise.service.notification.SlackMessage;
import com.expercise.service.notification.SlackNotificationService;
import com.expercise.service.user.AuthenticationService;
import com.expercise.service.util.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private SignatureGeneratorService signatureGeneratorService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ChallengeModelHelper challengeModelHelper;

    @Autowired
    private UserTestCaseStateService userTestCaseStateService;

    @Autowired
    private Configurations configurations;

    @Autowired
    private SlackNotificationService slackNotificationService;

    @Autowired
    private UrlService urlService;

    public List<Challenge> findAllChallengesOfUser() {
        return challengeRepository.findByUserOrderByCreateDateDesc(authenticationService.getCurrentUser());
    }

    public List<Challenge> findAllApprovedChallengesOfUser(User user) {
        return challengeRepository.findByApprovedIsTrueAndUser(user);
    }

    public List<Challenge> findAll() {
        return challengeRepository.findAllByOrderByCreateDateDesc();
    }

    public Challenge findById(Long id) {
        return challengeRepository.findOne(id);
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
            challengeRepository.save(challenge);
        } else {
            checkAndAutoApprove(challenge);

            challenge.setUser(authenticationService.getCurrentUser());
            challengeRepository.save(challenge);

            sendNewChallengeNotification(challenge);
        }

        return challenge.getId();
    }

    private void checkAndAutoApprove(Challenge challenge) {
        if ("auto".equalsIgnoreCase(configurations.getChallengeApprovalStrategy())) {
            challenge.setApproved(true);
        }
    }

    private void sendNewChallengeNotification(Challenge challenge) {
        User currentUser = authenticationService.getCurrentUser();
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.setChannel("#general");
        slackMessage.setText(
                String.format(
                        "A new challenge <%s|%s> added by <%s|%s>.",
                        urlService.challengeUrl(challenge),
                        challenge.getTitle(),
                        urlService.createUrlFor(currentUser.getBookmarkableUrl()),
                        currentUser.getFullName()
                ));
        slackNotificationService.sendMessage(slackMessage);
    }

    private Challenge prepareChallengeForSaving(ChallengeModel challengeModel) {
        Challenge challenge;
        if (challengeModel.getChallengeId() != null) {
            challenge = challengeRepository.findOne(challengeModel.getChallengeId());
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

    public Optional<Challenge> getDefaultChallenge() {
        return Optional.ofNullable(findById(configurations.getDefaultChallengeId()));
    }
}