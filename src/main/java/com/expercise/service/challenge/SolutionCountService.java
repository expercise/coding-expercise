package com.expercise.service.challenge;

import com.expercise.utils.caching.Caching;
import com.expercise.domain.challenge.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SolutionCountService implements Caching {

    private static final Map<Long, Long> SOLUTION_COUNTS = new ConcurrentHashMap<>();

    @Autowired
    private SolutionService solutionService;

    public Long getSolutionCountOf(Challenge challenge) {
        Long challengeId = challenge.getId();
        Long solutionCount = SOLUTION_COUNTS.get(challengeId);
        if (solutionCount == null) {
            solutionCount = solutionService.getSolutionCountOf(challenge);
            SOLUTION_COUNTS.put(challengeId, solutionCount);
        }
        return solutionCount;
    }

    public Map<Challenge, Long> prepareSolutionCountMapFor(List<Challenge> challenges) {
        Map<Challenge, Long> solutionCountMap = new HashMap<>();
        for (Challenge eachChallenge : challenges) {
            solutionCountMap.put(eachChallenge, getSolutionCountOf(eachChallenge));
        }
        return solutionCountMap;
    }

    public void clearCacheFor(Long challengeId) {
        SOLUTION_COUNTS.remove(challengeId);
    }

    @Override
    public void flush() {
        SOLUTION_COUNTS.clear();
    }

}
