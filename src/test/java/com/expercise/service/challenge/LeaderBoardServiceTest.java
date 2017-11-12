package com.expercise.service.challenge;

import com.expercise.service.cache.RedisCacheService;
import com.expercise.service.challenge.model.LeaderBoardModel;
import com.expercise.service.user.UserService;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LeaderBoardServiceTest {

    @Mock
    private UserPointService userPointService;

    @Mock
    private RedisCacheService cacheService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LeaderBoardService leaderBoardService;

    @Test
    public void shouldUpdateLeaderBoardPointForUser() {
        when(userPointService.getTotalPointsOf(1L)).thenReturn(10L);
        leaderBoardService.updateLeaderBoardPoint(1L);

        verify(cacheService).zAdd("points::leaderboard", 1L, 10L);
    }

    @Test
    public void shouldGetTop10UsersInLeaderBoard() {
        Set<ZSetOperations.TypedTuple<Serializable>> set = new LinkedHashSet<>();
        set.add(new DefaultTypedTuple(30L, 3d));
        set.add(new DefaultTypedTuple(20L, 2d));
        set.add(new DefaultTypedTuple(10L, 1d));

        when(cacheService.findScoresByKey("points::leaderboard", 0, 9)).thenReturn(set);
        when(userService.findById(10L)).thenReturn(new UserBuilder().id(10L).build());
        when(userService.findById(20L)).thenReturn(new UserBuilder().id(20L).build());
        when(userService.findById(30L)).thenReturn(new UserBuilder().id(30L).build());

        List<LeaderBoardModel> top10Users = leaderBoardService.getTop10UsersInLeaderBoard();
        assertThat(top10Users.get(0).getUser().getId(), equalTo(30L));
        assertThat(top10Users.get(0).getPoint(), equalTo(3));
        assertThat(top10Users.get(1).getUser().getId(), equalTo(20L));
        assertThat(top10Users.get(1).getPoint(), equalTo(2));
        assertThat(top10Users.get(2).getUser().getId(), equalTo(10L));
        assertThat(top10Users.get(2).getPoint(), equalTo(1));
    }

    @Test
    public void shouldGetRankForUserAsFirstIfUserIsTheLeader() {
        when(cacheService.zRevRankFor("points::leaderboard", 10L)).thenReturn(0L);
        Long rankFor = leaderBoardService.getRankFor(new UserBuilder().id(10L).build());
        assertThat(rankFor, equalTo(1L));
    }

    @Test
    public void shouldReturnUsersAroundUser() {
        Set<ZSetOperations.TypedTuple<Serializable>> set = new LinkedHashSet<>();
        set.add(new DefaultTypedTuple(50L, 5d));
        set.add(new DefaultTypedTuple(40L, 4d));
        set.add(new DefaultTypedTuple(30L, 3d));
        set.add(new DefaultTypedTuple(20L, 2d));
        set.add(new DefaultTypedTuple(10L, 1d));


        when(cacheService.zRevRankFor("points::leaderboard", 30L)).thenReturn(4L);
        when(cacheService.findScoresByKey("points::leaderboard", 3, 8)).thenReturn(set);
        when(userService.findById(10L)).thenReturn(new UserBuilder().id(10L).build());
        when(userService.findById(20L)).thenReturn(new UserBuilder().id(20L).build());
        when(userService.findById(30L)).thenReturn(new UserBuilder().id(30L).build());
        when(userService.findById(40L)).thenReturn(new UserBuilder().id(40L).build());
        when(userService.findById(50L)).thenReturn(new UserBuilder().id(50L).build());
        List<LeaderBoardModel> leaderBoardModelAroundUser = leaderBoardService.getLeaderBoardAroundUser(new UserBuilder().id(30L).build());
        assertThat(leaderBoardModelAroundUser.get(0).getUser().getId(), equalTo(50L));
        assertThat(leaderBoardModelAroundUser.get(1).getUser().getId(), equalTo(40L));
        assertThat(leaderBoardModelAroundUser.get(2).getUser().getId(), equalTo(30L));
        assertThat(leaderBoardModelAroundUser.get(3).getUser().getId(), equalTo(20L));
        assertThat(leaderBoardModelAroundUser.get(4).getUser().getId(), equalTo(10L));
    }

}