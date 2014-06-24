package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.UserPointDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.UserPoint;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.utils.DateUtils;
import com.ufukuzun.kodility.testutils.builder.ChallengeBuilder;
import com.ufukuzun.kodility.testutils.builder.UserBuilder;
import com.ufukuzun.kodility.utils.Clock;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserPointServiceTest {

    @InjectMocks
    private UserPointService service;

    @Mock
    private UserPointDao userPointDao;

    @Test
    public void shouldGiveUserPointForTheChallenge() {
        Clock.freeze(DateUtils.toDate("10/12/2012"));

        User user = new UserBuilder().id(2L).build();
        Challenge challenge = new ChallengeBuilder().id(3L).point(12).build();

        service.givePoint(challenge, user, ProgrammingLanguage.Python);

        ArgumentCaptor<UserPoint> pointCaptor = ArgumentCaptor.forClass(UserPoint.class);

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
        Challenge challenge = new ChallengeBuilder().id(3L).approved(true).build();

        when(userPointDao.countForPointGivingCriteria(challenge, user, ProgrammingLanguage.Python)).thenReturn(0L);

        assertTrue(service.canUserWinPoint(challenge, user, ProgrammingLanguage.Python));
    }

    @Test
    public void shouldNotBeAbleToWinPointIfUserHadWonPointFromTheChallengeBefore() {
        User user = new UserBuilder().id(2L).build();
        Challenge challenge = new ChallengeBuilder().id(3L).approved(true).build();

        when(userPointDao.countForPointGivingCriteria(challenge, user, ProgrammingLanguage.Python)).thenReturn(1L);

        assertFalse(service.canUserWinPoint(challenge, user, ProgrammingLanguage.Python));
    }

    @Test
    public void shouldNotBeAbleToWinPointIfChallengeIsNotApproved() {
        User user = new UserBuilder().id(2L).build();
        Challenge challenge = new ChallengeBuilder().id(3L).approved(false).build();

        assertFalse(service.canUserWinPoint(challenge, user, ProgrammingLanguage.Python));

        verifyZeroInteractions(userPointDao);
    }

}
