package com.expercise.service.challenge;

import com.expercise.domain.user.User;
import com.expercise.service.cache.CacheService;
import com.expercise.service.challenge.model.LeaderBoardModel;
import com.expercise.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderBoardService {

    private final static String LEADERBOARD = "points::leaderboard";

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private UserService userService;

    @Transactional
    public void updateLeaderBoardPoint(Long userId) {
        Long totalPointsOf = userPointService.getTotalPointsOf(userId);
        cacheService.zadd(LEADERBOARD, totalPointsOf, userId);
    }

    public List<LeaderBoardModel> getTop10UsersInLeaderBoard() {
        return cacheService.findTopX(LEADERBOARD, 10)
                .stream().map(tuple -> new LeaderBoardModel(userService.findById((Long) tuple.getValue()), tuple.getScore()))
                .collect(Collectors.toList());
    }

    public Long getRankFor(User user) {
        return cacheService.zRevRankFor(LEADERBOARD, user.getId()) + 1;
    }
}
