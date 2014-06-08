package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.UserPointDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.UserPoint;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.service.configuration.ConfigurationService;
import com.ufukuzun.kodility.testutils.TestDateUtils;
import com.ufukuzun.kodility.testutils.builder.ChallengeBuilder;
import com.ufukuzun.kodility.testutils.builder.UserBuilder;
import com.ufukuzun.kodility.testutils.builder.UserPointBuilder;import com.ufukuzun.kodility.utils.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserPointServiceTest {

    @InjectMocks
    private UserPointService service;

    @Mock
    private UserPointDao userPointDao;

    @Mock
    private ConfigurationService configurationService;

    @Test
    public void shouldGiveUserPointForTheChallenge() {
        Clock.freeze();
        Clock.setTime(TestDateUtils.toDate("10/12/2012"));

        User user = new UserBuilder().id(2L).build();
        Challenge challenge = new ChallengeBuilder().id(3L).build();

        when(configurationService.getValueAsInteger("challenge.defaultpointamount")).thenReturn(10);

        service.givePoint(challenge, user);

        ArgumentCaptor<UserPoint> pointCaptor = ArgumentCaptor.forClass(UserPoint.class);

        verify(userPointDao).save(pointCaptor.capture());
        UserPoint savedUserPoint = pointCaptor.getValue();

        assertThat(savedUserPoint.getChallenge(), equalTo(challenge));
        assertThat(savedUserPoint.getUser(), equalTo(user));
        assertThat(savedUserPoint.getPointAmount(), equalTo(10));
        assertThat(savedUserPoint.getGivenDate(), equalTo(Clock.getTime()));

        Clock.unfreeze();
    }

    @Test
    public void shouldReturnTrueIfUserHasNotWonPointFromTheChallengeBefore() {
        User user = new UserBuilder().id(2L).build();
        Challenge challenge = new ChallengeBuilder().id(3L).build();

        when(userPointDao.findByChallengeAndUser(challenge, user)).thenReturn(null);

        assertTrue(service.canUserWinPoint(challenge, user));

        verify(userPointDao).findByChallengeAndUser(challenge, user);
    }

    @Test
    public void shouldReturnFalseIfUserHasWonPointFromTheChallengeBefore() {
        User user = new UserBuilder().id(2L).build();
        Challenge challenge = new ChallengeBuilder().id(3L).build();
        UserPoint wonUserPoint = new UserPointBuilder().id(1L).user(user).challenge(challenge).build();

        when(userPointDao.findByChallengeAndUser(challenge, user)).thenReturn(wonUserPoint);

        assertFalse(service.canUserWinPoint(challenge, user));

        verify(userPointDao).findByChallengeAndUser(challenge, user);
    }


}
