package com.expercise.service.challenge;

import com.expercise.domain.user.User;
import com.expercise.service.cache.RedisCacheService;
import com.expercise.service.challenge.model.LeaderBoardModel;
import com.expercise.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LeaderBoardService {

    private final static String LEADERBOARD = "points::leaderboard";

    private final static int LEADERBOARD_TOP_USERS_COUNT = 10;

    private final static int LEADERBOARD_AROUND_USER_COUNT = 4;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private RedisCacheService cacheService;

    @Autowired
    private UserService userService;

    @Transactional
    public void updateLeaderBoardPoint(Long userId) {
        Long totalPointsOf = userPointService.getTotalPointsOf(userId);
        cacheService.zadd(LEADERBOARD, totalPointsOf, userId);
    }

    public List<LeaderBoardModel> getTop10UsersInLeaderBoard() {
        Set<ZSetOperations.TypedTuple<Serializable>> tuples = cacheService.findScoresByKey(LEADERBOARD, 0, LEADERBOARD_TOP_USERS_COUNT);
        return convertToModel(tuples);
    }

    private List<LeaderBoardModel> convertToModel(Set<ZSetOperations.TypedTuple<Serializable>> tuples) {
        return tuples.stream()
                .map(tuple -> new LeaderBoardModel(userService.findById((Long) tuple.getValue()), tuple.getScore()))
                .collect(Collectors.toList());
    }

    public Long getRankFor(User user) {
        return cacheService.zRevRankFor(LEADERBOARD, user.getId()) + 1;
    }

    public List<LeaderBoardModel> getLeaderBoardAroundUser(User user) {
        Long rank = getRankFor(user);

        if (rank == null) {
            return Collections.emptyList();
        }

        int start = rank.intValue() - (LEADERBOARD_AROUND_USER_COUNT / 2);
        start = start < 0 ? 0 : start;
        int end = start + LEADERBOARD_AROUND_USER_COUNT + 1;

        Set<ZSetOperations.TypedTuple<Serializable>> tuples = cacheService.findScoresByKey(LEADERBOARD, start, end);

        return convertToModel(tuples);
    }

}
