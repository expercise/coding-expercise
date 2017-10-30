package com.expercise.repository.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.user.User;
import com.expercise.repository.BaseRepository;

import java.util.List;

public interface ChallengeRepository extends BaseRepository<Challenge> {

    List<Challenge> findByApprovedIsTrue();

    List<Challenge> findByUserOrderByCreateDateDesc(User user);

    List<Challenge> findAllByOrderByCreateDateDesc();

    List<Challenge> findByApprovedIsTrueAndUser(User user);

}
