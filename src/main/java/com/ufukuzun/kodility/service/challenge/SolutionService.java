package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.controller.challenge.model.UserSolutionModel;
import com.ufukuzun.kodility.dao.challenge.SolutionDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SolutionService {

    @Autowired
    private SolutionDao solutionDao;

    @Autowired
    private AuthenticationService authenticationService;

    @Transactional
    public void saveSolution(Solution solution) {
        solutionDao.save(solution);
    }

    public Solution getSolutionBy(Challenge challenge, User user, ProgrammingLanguage programmingLanguage) {
        return solutionDao.findBy(challenge, user, programmingLanguage);
    }

    public List<Solution> getAllApprovedChallengeSolutionsOf(User user) {
        return solutionDao.findApprovedChallengeSolutionsByUser(user);
    }

    public void updateSolution(Solution solution) {
        solutionDao.update(solution);
    }

    public long getSolutionCountOf(Challenge challenge) {
        return solutionDao.countByChallenge(challenge);
    }

    public ArrayList<UserSolutionModel> getUserSolutionModels(Challenge challenge) {
        ArrayList<UserSolutionModel> userSolutionModels = new ArrayList<>();
        for (Solution solution : getSolutionsOfUser(challenge)) {
            UserSolutionModel userSolutionModel = UserSolutionModel.createFrom(solution);
            userSolutionModels.add(userSolutionModel);
        }

        Collections.sort(userSolutionModels);
        return userSolutionModels;
    }

    public Set<Challenge> getSolvedChallengesOf(User user) {
        return getAllApprovedChallengeSolutionsOf(user).stream()
                .map(Solution::getChallenge)
                .collect(Collectors.toSet());
    }

    private List<Solution> getSolutionsOfUser(Challenge challenge) {
        User currentUser = authenticationService.getCurrentUser();
        return solutionDao.findSolutionsBy(challenge, currentUser);
    }

}