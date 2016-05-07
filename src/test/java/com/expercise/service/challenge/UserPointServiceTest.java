package com.expercise.service.challenge;

import com.expercise.dao.challenge.UserPointDao;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.UserPoint;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.service.cache.RedisCacheService;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.UserBuilder;
import com.expercise.utils.Clock;
import com.expercise.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserPointServiceTest {

    @InjectMocks
    private UserPointService service;

    @Mock
    private UserPointDao userPointDao;

    @Mock
    private RedisCacheService cacheService;

    @Test
    public void shouldGiveUserPointForTheChallenge() {
        Clock.freeze(DateUtils.toDate("10/12/2012"));

        User user = new UserBuilder().id(2L).build();
        Challenge challenge = new ChallengeBuilder().id(3L).point(12).build();

        service.givePoint(challenge, user, ProgrammingLanguage.Python);

        ArgumentCaptor<UserPoint> pointCaptor = ArgumentCaptor.forClass(UserPoint.class);

        verify(cacheService).rightPush("points::leaderboard::queue", 2L);
        verify(userPointDao).save(pointCaptor.capture());
        UserPoint savedUserPoint = pointCaptor.getValue();

        assertThat(savedUserPoint.getChallenge(), equalTo(challenge));
        assertThat(savedUserPoint.getUser(), equalTo(user));
        assertThat(savedUserPoint.getProgrammingLanguage(), equalTo(ProgrammingLanguage.Python));
        assertThat(savedUserPoint.getPointAmount(), equalTo(12));
        assertThat(savedUserPoint.getGivenDate(), equalTo(Clock.getTime()));

        Clock.unfreeze();
    }

    @Test
    public void shouldBeAbleToWinPointIfUserHadNotWonPointFromTheChallengeBeforeAndChallengeIsApproved() {
        User user = new UserBuilder().id(2L).build();
        Challenge challenge = new ChallengeBuilder().id(3L).user(new UserBuilder().build()).approved(true).build();

        when(userPointDao.countForPointGivingCriteria(challenge, user, ProgrammingLanguage.Python)).thenReturn(0L);

        assertTrue(service.canUserWinPoint(challenge, user, ProgrammingLanguage.Python));
    }

    @Test
    public void shouldNotBeAbleToWinPointIfUserHadWonPointFromTheChallengeBefore() {
        User user = new UserBuilder().id(2L).build();
        Challenge challenge = new ChallengeBuilder().id(3L).user(new UserBuilder().build()).approved(true).build();

        when(userPointDao.countForPointGivingCriteria(challenge, user, ProgrammingLanguage.Python)).thenReturn(1L);

        assertFalse(service.canUserWinPoint(challenge, user, ProgrammingLanguage.Python));
    }

    @Test
    public void shouldNotBeAbleToWinPointIfChallengeIsNotApproved() {
        User user = new UserBuilder().id(2L).build();
        Challenge challenge = new ChallengeBuilder().id(3L).user(new UserBuilder().build()).approved(false).build();

        assertFalse(service.canUserWinPoint(challenge, user, ProgrammingLanguage.Python));

        verifyZeroInteractions(userPointDao);
    }

    @Test
    public void shouldNotBeAbleToWinPointIfChallengeAuthorIsSamePersonAsUser() {
        User user = new UserBuilder().id(2L).build();
        Challenge challenge = new ChallengeBuilder().id(3L).user(user).approved(true).build();

        assertFalse(service.canUserWinPoint(challenge, user, ProgrammingLanguage.Python));

        verifyZeroInteractions(userPointDao);
    }

}
