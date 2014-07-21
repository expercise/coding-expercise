package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.SolutionDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SolutionService {

    @Autowired
    private SolutionDao solutionDao;

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

}