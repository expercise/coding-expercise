package com.kodility.service.challenge;

import com.kodility.controller.challenge.model.UserSolutionModel;
import com.kodility.dao.challenge.SolutionDao;
import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.Solution;
import com.kodility.domain.level.Level;
import com.kodility.domain.user.User;
import com.kodility.enums.ProgrammingLanguage;
import com.kodility.service.user.AuthenticationService;
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

    public List<Solution> getAllSolutionsInLevelsOf(User user, List<Level> levels) {
        if (levels.isEmpty()) {
            return Collections.emptyList();
        }
        return solutionDao.findAllSolutionsInLevelsOf(user, levels);
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
        return solutionDao.findApprovedChallengeSolutionsByUser(user).stream()
                .map(Solution::getChallenge)
                .collect(Collectors.toSet());
    }

    private List<Solution> getSolutionsOfUser(Challenge challenge) {
        User currentUser = authenticationService.getCurrentUser();
        return solutionDao.findSolutionsBy(challenge, currentUser);
    }

}