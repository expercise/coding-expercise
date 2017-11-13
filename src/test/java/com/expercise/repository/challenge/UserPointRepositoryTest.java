package com.expercise.repository.challenge;

import com.expercise.BaseSpringIntegrationTest;
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

public class UserPointRepositoryTest extends BaseSpringIntegrationTest {

    @Autowired
    private UserPointRepository repository;

    @Test
    public void shouldReturnUserPointByChallengeAndUser() {
        User user = new UserBuilder().email("user@expercise.com").persist(getEntityManager());

        User author = new UserBuilder().email("author@expercise.com").persist(getEntityManager());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getEntityManager());

        UserPoint userPoint = new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python2).givenDate(Clock.getTime()).pointAmount(10).persist(getEntityManager());

        flushAndClear();

        UserPoint foundUserPoint = repository.findByChallengeAndUser(challenge, user);

        assertThat(foundUserPoint, equalTo(userPoint));
    }

    @Test
    public void shouldReturnNullWhenNotFoundByChallengeAndUser() {
        User user = new UserBuilder().email("user@expercise.com").persist(getEntityManager());

        User author = new UserBuilder().email("author@expercise.com").persist(getEntityManager());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getEntityManager());

        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python2).pointAmount(10).givenDate(Clock.getTime()).persist(getEntityManager());

        flushAndClear();

        UserPoint foundUserPoint = repository.findByChallengeAndUser(challenge, author);

        assertThat(foundUserPoint, nullValue());
    }

    @Test
    public void shouldReturnCountByChallengeAndUser() {
        User user = new UserBuilder().email("user@expercise.com").persist(getEntityManager());

        User author = new UserBuilder().email("author@expercise.com").persist(getEntityManager());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getEntityManager());

        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python2).givenDate(Clock.getTime()).pointAmount(10).persist(getEntityManager());
        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python2).givenDate(Clock.getTime()).pointAmount(5).persist(getEntityManager());
        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.JavaScript).givenDate(Clock.getTime()).pointAmount(10).persist(getEntityManager());

        flushAndClear();

        long count = repository.countByChallengeAndUserAndProgrammingLanguage(challenge, user, ProgrammingLanguage.Python2);

        assertThat(count, equalTo(2L));
    }

    @Test
    public void shouldReturnZeroAsCountWhenNotFoundByPointGivingCriteria() {
        User user = new UserBuilder().email("user@expercise.com").persist(getEntityManager());

        User author = new UserBuilder().email("author@expercise.com").persist(getEntityManager());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getEntityManager());

        new UserPointBuilder().challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python2).pointAmount(10).givenDate(Clock.getTime()).persist(getEntityManager());

        flushAndClear();

        long count = repository.countByChallengeAndUserAndProgrammingLanguage(challenge, author, ProgrammingLanguage.Python2);

        assertThat(count, equalTo(0L));
    }

    @Test
    public void shouldReturnTotalExperiencePointsOfUser() {
        User user = new UserBuilder().email("user@expercise.com").persist(getEntityManager());

        Challenge challenge = new ChallengeBuilder().user(user).persist(getEntityManager());

        new UserPointBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.Python2).user(user).pointAmount(6).givenDate(Clock.getTime()).persist(getEntityManager());
        new UserPointBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.Python2).user(user).pointAmount(7).givenDate(Clock.getTime()).persist(getEntityManager());

        flushAndClear();

        Long totalPoints = repository.getTotalPointsOf(user.getId());

        assertThat(totalPoints, equalTo(13L));
    }

    @Test
    public void shouldReturnZeroAsTotalExperiencePointsOfUserIfUserHasNotEarnedPointYet() {
        User user = new UserBuilder().email("user@expercise.com").persist(getEntityManager());

        flushAndClear();

        Long totalPoints = repository.getTotalPointsOf(user.getId());

        assertThat(totalPoints, nullValue());
    }

}