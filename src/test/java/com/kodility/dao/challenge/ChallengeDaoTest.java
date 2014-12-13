package com.kodility.dao.challenge;

import com.kodility.AbstractDaoTest;
import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.level.Level;
import com.kodility.domain.user.User;
import com.kodility.testutils.builder.ChallengeBuilder;
import com.kodility.testutils.builder.LevelBuilder;
import com.kodility.testutils.builder.UserBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.kodility.testutils.asserts.Asserts.assertExpectedItems;
import static com.kodility.testutils.asserts.Asserts.assertNotExpectedItems;

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
    public void shouldFindNotLeveledApprovedChallenges() {
        User user = new UserBuilder().persist(getCurrentSession());
        Level level = new LevelBuilder().persist(getCurrentSession());
        Challenge challenge1 = new ChallengeBuilder().user(user).approved(false).persist(getCurrentSession());
        Challenge challenge2 = new ChallengeBuilder().user(user).level(level).approved(true).persist(getCurrentSession());
        Challenge challenge3 = new ChallengeBuilder().user(user).approved(true).persist(getCurrentSession());

        flushAndClear();

        List<Challenge> resultList = dao.findNotLeveledApprovedChallenges();

        assertExpectedItems(resultList, challenge3);
        assertNotExpectedItems(resultList, challenge1, challenge2);
    }

}
