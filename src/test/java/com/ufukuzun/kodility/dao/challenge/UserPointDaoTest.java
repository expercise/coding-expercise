package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.AbstractDaoTest;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.UserPoint;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.testutils.builder.ChallengeBuilder;
import com.ufukuzun.kodility.testutils.builder.UserBuilder;
import com.ufukuzun.kodility.testutils.builder.UserPointBuilder;
import com.ufukuzun.kodility.utils.Clock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class UserPointDaoTest extends AbstractDaoTest {

    @Autowired
    private UserPointDao dao;

    @Test
    public void shouldReturnUserPointByChallengeAndUser() {
        User user = new UserBuilder().email("user@kodility.com").persist(getCurrentSession());

        User author = new UserBuilder().email("author@kodility.com").persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());

        UserPoint userPoint = new UserPointBuilder().challenge(challenge).user(user).givenDate(Clock.getTime()).pointAmount(10).persist(getCurrentSession());

        UserPoint foundUserPoint = dao.findByChallengeAndUser(challenge, user);

        assertThat(foundUserPoint, equalTo(userPoint));
    }

    @Test
    public void shouldReturnNullWhenNotFoundByChallengeAndUser() {
        User user = new UserBuilder().email("user@kodility.com").persist(getCurrentSession());

        User author = new UserBuilder().email("author@kodility.com").persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());

        new UserPointBuilder().challenge(challenge).user(user).pointAmount(10).givenDate(Clock.getTime()).persist(getCurrentSession());

        UserPoint foundUserPoint = dao.findByChallengeAndUser(challenge, author);

        assertThat(foundUserPoint, nullValue());
    }

    @Test
    public void shouldReturnCountByChallengeAndUser() {
        User user = new UserBuilder().email("user@kodility.com").persist(getCurrentSession());

        User author = new UserBuilder().email("author@kodility.com").persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());

        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python).givenDate(Clock.getTime()).pointAmount(10).persist(getCurrentSession());
        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python).givenDate(Clock.getTime()).pointAmount(5).persist(getCurrentSession());
        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.JavaScript).givenDate(Clock.getTime()).pointAmount(10).persist(getCurrentSession());

        long count = dao.countForPointGivingCriteria(challenge, user, ProgrammingLanguage.Python);

        assertThat(count, equalTo(2L));
    }

    @Test
    public void shouldReturnZeroAsCountWhenNotFoundByPointGivingCriteria() {
        User user = new UserBuilder().email("user@kodility.com").persist(getCurrentSession());

        User author = new UserBuilder().email("author@kodility.com").persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());

        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python).pointAmount(10).givenDate(Clock.getTime()).persist(getCurrentSession());

        long count = dao.countForPointGivingCriteria(challenge, author, ProgrammingLanguage.Python);

        assertThat(count, equalTo(0L));
    }

    @Test
    public void shouldReturnTotalExperiencePointsOfUser() {
        User user = new UserBuilder().email("user@kodility.com").persist(getCurrentSession());

        Challenge challenge = new ChallengeBuilder().user(user).persist(getCurrentSession());

        new UserPointBuilder().challenge(challenge).user(user).pointAmount(6).givenDate(Clock.getTime()).persist(getCurrentSession());
        new UserPointBuilder().challenge(challenge).user(user).pointAmount(7).givenDate(Clock.getTime()).persist(getCurrentSession());

        long totalPoints = dao.getTotalPointsOf(user);

        assertThat(totalPoints, equalTo(13L));
    }

    @Test
    public void shouldReturnZeroAsTotalExperiencePointsOfUserIfUserHasNotEarnedPointYet() {
        User user = new UserBuilder().email("user@kodility.com").persist(getCurrentSession());

        long totalPoints = dao.getTotalPointsOf(user);

        assertThat(totalPoints, equalTo(0L));
    }

}