package com.expercise.repository.challenge;

import com.expercise.BaseSpringIntegrationTest;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.level.Level;
import com.expercise.domain.theme.Theme;
import com.expercise.domain.user.User;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.LevelBuilder;
import com.expercise.testutils.builder.ThemeBuilder;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.expercise.testutils.asserts.Asserts.assertExpectedItems;
import static com.expercise.testutils.asserts.Asserts.assertNotExpectedItems;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ChallengeRepositoryTest extends BaseSpringIntegrationTest {

    @Autowired
    private ChallengeRepository repository;

    @Test
    public void shouldFindAllApproved() {
        User user = new UserBuilder().persist(getEntityManager());
        Challenge challenge1 = new ChallengeBuilder().user(user).approved(true).persist(getEntityManager());
        Challenge challenge2 = new ChallengeBuilder().user(user).approved(false).persist(getEntityManager());
        Challenge challenge3 = new ChallengeBuilder().user(user).approved(true).persist(getEntityManager());

        List<Challenge> resultList = repository.findByApprovedIsTrue();

        assertExpectedItems(resultList, challenge1, challenge3);
        assertNotExpectedItems(resultList, challenge2);
    }

    @Test
    public void shouldFindAllByUser() {
        User user1 = new UserBuilder().persist(getEntityManager());
        User user2 = new UserBuilder().persist(getEntityManager());
        Challenge challenge1 = new ChallengeBuilder().user(user2).persist(getEntityManager());
        Challenge challenge2 = new ChallengeBuilder().user(user1).persist(getEntityManager());
        Challenge challenge3 = new ChallengeBuilder().user(user1).persist(getEntityManager());

        List<Challenge> resultList = repository.findByApprovedIsTrueAndUser(user1);

        assertExpectedItems(resultList, challenge2, challenge3);
        assertNotExpectedItems(resultList, challenge1);
    }

    @Test
    public void shouldFindNotThemedApprovedChallenges() {
        User user = new UserBuilder().persist(getEntityManager());

        Level themedLevel = new LevelBuilder().persist(getEntityManager());
        new ThemeBuilder().levels(themedLevel).persist(getEntityManager());

        Level notThemedLevel = new LevelBuilder().persist(getEntityManager());

        Challenge challenge1 = new ChallengeBuilder().user(user).approved(false).persist(getEntityManager());
        Challenge challenge2 = new ChallengeBuilder().user(user).level(themedLevel).approved(true).persist(getEntityManager());
        Challenge challenge3 = new ChallengeBuilder().user(user).approved(true).persist(getEntityManager());
        Challenge challenge4 = new ChallengeBuilder().user(user).level(notThemedLevel).approved(true).persist(getEntityManager());

        List<Challenge> resultList = repository.findNotThemedApprovedChallenges();

        assertExpectedItems(resultList, challenge3, challenge4);
        assertNotExpectedItems(resultList, challenge1, challenge2);
    }

    @Test
    public void shouldCountNotThemedApprovedChallenges() {
        User user = new UserBuilder().persist(getEntityManager());

        Level themedLevel = new LevelBuilder().persist(getEntityManager());
        new ThemeBuilder().levels(themedLevel).persist(getEntityManager());

        Level notThemedLevel = new LevelBuilder().persist(getEntityManager());

        new ChallengeBuilder().user(user).approved(false).persist(getEntityManager());
        new ChallengeBuilder().user(user).level(themedLevel).approved(true).persist(getEntityManager());
        new ChallengeBuilder().user(user).approved(true).persist(getEntityManager());
        new ChallengeBuilder().user(user).level(notThemedLevel).approved(true).persist(getEntityManager());
        new ChallengeBuilder().user(user).level(notThemedLevel).approved(true).persist(getEntityManager());

        Long count = repository.countNotThemedApprovedChallenges();

        assertThat(count, equalTo(3L));
    }

    @Test
    public void shouldCountApprovedChallengesByTheme() {
        User user = new UserBuilder().persist(getEntityManager());

        Level level1 = new LevelBuilder().persist(getEntityManager());
        Level level2 = new LevelBuilder().persist(getEntityManager());
        Level level3 = new LevelBuilder().persist(getEntityManager());
        Theme theme1 = new ThemeBuilder().levels(level1, level2).persist(getEntityManager());
        Theme theme2 = new ThemeBuilder().levels(level3).persist(getEntityManager());

        Level notThemedLevel = new LevelBuilder().persist(getEntityManager());

        // Not approved
        new ChallengeBuilder().user(user).approved(false).persist(getEntityManager());

        // Not themed but approved
        new ChallengeBuilder().user(user).level(notThemedLevel).approved(true).persist(getEntityManager());

        // Not themed and not approved
        new ChallengeBuilder().user(user).level(notThemedLevel).approved(false).persist(getEntityManager());

        // Not approved but themed by "theme1"
        new ChallengeBuilder().user(user).level(level1).approved(false).persist(getEntityManager());
        new ChallengeBuilder().user(user).level(level2).approved(false).persist(getEntityManager());

        // Approved and themed by "theme1"
        new ChallengeBuilder().user(user).level(level1).approved(true).persist(getEntityManager());
        new ChallengeBuilder().user(user).level(level2).approved(true).persist(getEntityManager());
        new ChallengeBuilder().user(user).level(level2).approved(true).persist(getEntityManager());
        new ChallengeBuilder().user(user).level(level2).approved(true).persist(getEntityManager());

        // Approved and themed by "theme2"
        new ChallengeBuilder().user(user).level(level3).approved(true).persist(getEntityManager());
        new ChallengeBuilder().user(user).level(level3).approved(true).persist(getEntityManager());

        assertThat(repository.countByApprovedIsTrueAndLevelTheme(theme1), equalTo(4L));
        assertThat(repository.countByApprovedIsTrueAndLevelTheme(theme2), equalTo(2L));
    }

}
