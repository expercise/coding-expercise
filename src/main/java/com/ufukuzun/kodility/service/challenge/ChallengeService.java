package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.ChallengeRepository;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    public Challenge findById(String id) {
        return challengeRepository.findById(id);
    }

    // TODO ufuk: complete - order by difficulty and find easiest challenge
    public Challenge findEasiestOne() {
        return challengeRepository.findAll().iterator().next();
    }

}
