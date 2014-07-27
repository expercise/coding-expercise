package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.controller.challenge.model.ChallengeModel;
import com.ufukuzun.kodility.dao.challenge.ChallengeDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.domain.user.User;
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

    @Autowired
    private ChallengeModelHelper challengeModelHelper;

    @Autowired
    private SolutionService solutionService;

    public List<Challenge> findAllApproved() {
        return challengeDao.findAllApproved();
    }

    public List<Challenge> findAllChallengesOfUser() {
        return challengeDao.findAllByUser(authenticationService.getCurrentUser());
    }

    public List<Challenge> findAll() {
        return challengeDao.findAll();
    }

    public Challenge findById(Long id) {
        return challengeDao.findOne(id);
    }

    public List<Challenge> findNotLeveledApprovedChallenges() {
        return challengeDao.findNotLeveledApprovedChallenges();
    }

    public Map<String, String> prepareSignaturesMapFor(Challenge challenge) {
        User user = authenticationService.getCurrentUser();
        Map<String, String> signaturesMap = new HashMap<>();
        for (ProgrammingLanguage language : ProgrammingLanguage.values()) {
            Solution solution = solutionService.getSolutionBy(challenge, user, language);
            if (solution == null) {
                String signature = signatureGeneratorService.generatorSignatureFor(challenge, language);
                signaturesMap.put(language.getShortName(), signature);
            } else {
                signaturesMap.put(language.getShortName(), solution.getSolution());
            }
        }

        return signaturesMap;
    }

    public Long saveChallenge(ChallengeModel challengeModel) {
        Challenge challenge = prepareChallengeForSaving(challengeModel);

        if (challenge.isPersisted()) {
            challengeDao.update(challenge);
        } else {
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

}
