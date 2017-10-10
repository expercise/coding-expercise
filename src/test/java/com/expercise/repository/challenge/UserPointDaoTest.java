package com.expercise.repository.challenge;

import com.expercise.BaseRepositoryTest;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.UserPoint;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.UserBuilder;
import com.expercise.testutils.builder.UserPointBuilder;
import com.expercise.utils.Clock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class UserPointDaoTest extends BaseRepositoryTest {

    @Autowired
    private UserPointDao dao;

    @Test
    public void shouldReturnUserPointByChallengeAndUser() {
        User user = new UserBuilder().email("user@expercise.com").persist(getCurrentSession());

        User author = new UserBuilder().email("author@expercise.com").persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());

        UserPoint userPoint = new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python).givenDate(Clock.getTime()).pointAmount(10).persist(getCurrentSession());

        flushAndClear();

        UserPoint foundUserPoint = dao.findByChallengeAndUser(challenge, user);

        assertThat(foundUserPoint, equalTo(userPoint));
    }

    @Test
    public void shouldReturnNullWhenNotFoundByChallengeAndUser() {
        User user = new UserBuilder().email("user@expercise.com").persist(getCurrentSession());

        User author = new UserBuilder().email("author@expercise.com").persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());

        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python).pointAmount(10).givenDate(Clock.getTime()).persist(getCurrentSession());

        flushAndClear();

        UserPoint foundUserPoint = dao.findByChallengeAndUser(challenge, author);

        assertThat(foundUserPoint, nullValue());
    }

    @Test
    public void shouldReturnCountByChallengeAndUser() {
        User user = new UserBuilder().email("user@expercise.com").persist(getCurrentSession());

        User author = new UserBuilder().email("author@expercise.com").persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());

        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python).givenDate(Clock.getTime()).pointAmount(10).persist(getCurrentSession());
        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python).givenDate(Clock.getTime()).pointAmount(5).persist(getCurrentSession());
        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.JavaScript).givenDate(Clock.getTime()).pointAmount(10).persist(getCurrentSession());

        flushAndClear();

        long count = dao.countForPointGivingCriteria(challenge, user, ProgrammingLanguage.Python);

        assertThat(count, equalTo(2L));
    }

    @Test
    public void shouldReturnZeroAsCountWhenNotFoundByPointGivingCriteria() {
        User user = new UserBuilder().email("user@expercise.com").persist(getCurrentSession());

        User author = new UserBuilder().email("author@expercise.com").persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());

        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python).pointAmount(10).givenDate(Clock.getTime()).persist(getCurrentSession());

        flushAndClear();

        long count = dao.countForPointGivingCriteria(challenge, author, ProgrammingLanguage.Python);

        assertThat(count, equalTo(0L));
    }

    @Test
    public void shouldReturnTotalExperiencePointsOfUser() {
        User user = new UserBuilder().email("user@expercise.com").persist(getCurrentSession());

        Challenge challenge = new ChallengeBuilder().user(user).persist(getCurrentSession());

        new UserPointBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.Python).user(user).pointAmount(6).givenDate(Clock.getTime()).persist(getCurrentSession());
        new UserPointBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.Python).user(user).pointAmount(7).givenDate(Clock.getTime()).persist(getCurrentSession());

        flushAndClear();

        long totalPoints = dao.getTotalPointsOf(user.getId());

        assertThat(totalPoints, equalTo(13L));
    }

    @Test
    public void shouldReturnZeroAsTotalExperiencePointsOfUserIfUserHasNotEarnedPointYet() {
        User user = new UserBuilder().email("user@expercise.com").persist(getCurrentSession());

        flushAndClear();

        long totalPoints = dao.getTotalPointsOf(user.getId());

        assertThat(totalPoints, equalTo(0L));
    }

}