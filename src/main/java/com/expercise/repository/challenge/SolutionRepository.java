package com.expercise.repository.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.Solution;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.repository.BaseRepository;

import java.util.List;

public interface SolutionRepository extends BaseRepository<Solution> {

    Solution findByChallengeAndUserAndProgrammingLanguage(Challenge challenge, User user, ProgrammingLanguage programmingLanguage);

    List<Solution> findByChallengeApprovedIsTrueAndUser(User user);

    List<Solution> findByChallengeAndUser(Challenge challenge, User user);

    Long countByChallenge(Challenge challenge);

    Long countByUser(User user);

}
