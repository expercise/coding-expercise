package com.expercise.repository.challenge;

import com.expercise.BaseSpringIntegrationTest;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.user.User;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.expercise.testutils.asserts.Asserts.assertExpectedItems;
import static com.expercise.testutils.asserts.Asserts.assertNotExpectedItems;

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

}
