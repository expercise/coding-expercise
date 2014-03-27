package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.controller.challenge.SampleChallenges;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import org.springframework.stereotype.Service;

@Service
public class ChallengeService {

    // TODO ufuk: change after database configuration
    public Challenge findById(Long id) {
        return SampleChallenges.sampleChallenges.get(0);
    }

}
