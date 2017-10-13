package com.expercise.repository.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.UserPoint;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserPointRepository extends BaseRepository<UserPoint> {

    UserPoint findByChallengeAndUser(Challenge challenge, User user);

    Long countByChallengeAndUserAndProgrammingLanguage(Challenge challenge, User user, ProgrammingLanguage programmingLanguage);

    @Query("select sum(up.pointAmount) from UserPoint up where up.user.id = ?1")
    Long getTotalPointsOf(Long userId);

}
