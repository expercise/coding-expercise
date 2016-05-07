package com.expercise.dao.challenge;

import com.expercise.AbstractDaoTest;
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

public class ChallengeDaoTest extends AbstractDaoTest {

    @Autowired
    private ChallengeDao dao;

    @Test
    public void shouldFindAllApproved() {
        User user = new UserBuilder().persist(getCurrentSession());
        Challenge challenge1 = new ChallengeBuilder().user(user).approved(true).persist(getCurrentSession());
        Challenge challenge2 = new ChallengeBuilder().user(user).approved(false).persist(getCurrentSession());
        Challenge challenge3 = new ChallengeBuilder().user(user).approved(true).persist(getCurrentSession());

        flushAndClear();

        List<Challenge> resultList = dao.findAllApproved();

        assertExpectedItems(resultList, challenge1, challenge3);
        assertNotExpectedItems(resultList, challenge2);
    }

    @Test
    public void shouldFindAllByUser() {
        User user1 = new UserBuilder().persist(getCurrentSession());
        User user2 = new UserBuilder().persist(getCurrentSession());
        Challenge challenge1 = new ChallengeBuilder().user(user2).persist(getCurrentSession());
        Challenge challenge2 = new ChallengeBuilder().user(user1).persist(getCurrentSession());
        Challenge challenge3 = new ChallengeBuilder().user(user1).persist(getCurrentSession());

        flushAndClear();

        List<Challenge> resultList = dao.findAllByUser(user1);

        assertExpectedItems(resultList, challenge2, challenge3);
        assertNotExpectedItems(resultList, challenge1);
    }

    @Test
    public void shouldFindNotThemedApprovedChallenges() {
        User user = new UserBuilder().persist(getCurrentSession());

        Level themedLevel = new LevelBuilder().persist(getCurrentSession());
        new ThemeBuilder().levels(themedLevel).persist(getCurrentSession());

        Level notThemedLevel = new LevelBuilder().persist(getCurrentSession());

        Challenge challenge1 = new ChallengeBuilder().user(user).approved(false).persist(getCurrentSession());
        Challenge challenge2 = new ChallengeBuilder().user(user).level(themedLevel).approved(true).persist(getCurrentSession());
        Challenge challenge3 = new ChallengeBuilder().user(user).approved(true).persist(getCurrentSession());
        Challenge challenge4 = new ChallengeBuilder().user(user).level(notThemedLevel).approved(true).persist(getCurrentSession());

        flushAndClear();

        List<Challenge> resultList = dao.findNotThemedApprovedChallenges();

        assertExpectedItems(resultList, challenge3, challenge4);
        assertNotExpectedItems(resultList, challenge1, challenge2);
    }

    @Test
    public void shouldCountNotThemedApprovedChallenges() {
        User user = new UserBuilder().persist(getCurrentSession());

        Level themedLevel = new LevelBuilder().persist(getCurrentSession());
        new ThemeBuilder().levels(themedLevel).persist(getCurrentSession());

        Level notThemedLevel = new LevelBuilder().persist(getCurrentSession());

        new ChallengeBuilder().user(user).approved(false).persist(getCurrentSession());
        new ChallengeBuilder().user(user).level(themedLevel).approved(true).persist(getCurrentSession());
        new ChallengeBuilder().user(user).approved(true).persist(getCurrentSession());
        new ChallengeBuilder().user(user).level(notThemedLevel).approved(true).persist(getCurrentSession());
        new ChallengeBuilder().user(user).level(notThemedLevel).approved(true).persist(getCurrentSession());

        flushAndClear();

        Long count = dao.countNotThemedApprovedChallenges();

        assertThat(count, equalTo(3L));
    }

    @Test
    public void shouldCountApprovedChallengesByTheme() {
        User user = new UserBuilder().persist(getCurrentSession());

        Level level1 = new LevelBuilder().persist(getCurrentSession());
        Level level2 = new LevelBuilder().persist(getCurrentSession());
        Level level3 = new LevelBuilder().persist(getCurrentSession());
        Theme theme1 = new ThemeBuilder().levels(level1, level2).persist(getCurrentSession());
        Theme theme2 = new ThemeBuilder().levels(level3).persist(getCurrentSession());

        Level notThemedLevel = new LevelBuilder().persist(getCurrentSession());

        // Not approved
        new ChallengeBuilder().user(user).approved(false).persist(getCurrentSession());

        // Not themed but approved
        new ChallengeBuilder().user(user).level(notThemedLevel).approved(true).persist(getCurrentSession());

        // Not themed and not approved
        new ChallengeBuilder().user(user).level(notThemedLevel).approved(false).persist(getCurrentSession());

        // Not approved but themed by "theme1"
        new ChallengeBuilder().user(user).level(level1).approved(false).persist(getCurrentSession());
        new ChallengeBuilder().user(user).level(level2).approved(false).persist(getCurrentSession());

        // Approved and themed by "theme1"
        new ChallengeBuilder().user(user).level(level1).approved(true).persist(getCurrentSession());
        new ChallengeBuilder().user(user).level(level2).approved(true).persist(getCurrentSession());
        new ChallengeBuilder().user(user).level(level2).approved(true).persist(getCurrentSession());
        new ChallengeBuilder().user(user).level(level2).approved(true).persist(getCurrentSession());

        // Approved and themed by "theme2"
        new ChallengeBuilder().user(user).level(level3).approved(true).persist(getCurrentSession());
        new ChallengeBuilder().user(user).level(level3).approved(true).persist(getCurrentSession());

        flushAndClear();

        assertThat(dao.countApprovedChallengesIn(theme1), equalTo(4L));
        assertThat(dao.countApprovedChallengesIn(theme2), equalTo(2L));
    }

}
