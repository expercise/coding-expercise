package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.AbstractDaoTest;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.testutils.builder.ChallengeBuilder;
import com.ufukuzun.kodility.testutils.builder.SolutionBuilder;
import com.ufukuzun.kodility.testutils.builder.UserBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SolutionDaoTest extends AbstractDaoTest {

    @Autowired
    private SolutionDao dao;

    @Test
    public void shouldFindSolutionByChallengeAndUser() {
        User author = new UserBuilder().persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());
        User user = new UserBuilder().persist(getCurrentSession());
        Solution solution = new SolutionBuilder().challenge(challenge).solution("solution").user(user).persist(getCurrentSession());

        Solution foundSolution = dao.findByChallengeAndUser(challenge, user);

        assertThat(foundSolution, equalTo(solution));
    }

    @Test
    public void shouldNotFindSolutionByDifferentChallengeAndUser() {
        User author = new UserBuilder().persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());
        Challenge differentChallenge = new ChallengeBuilder().user(author).persist(getCurrentSession());
        User user = new UserBuilder().persist(getCurrentSession());
        new SolutionBuilder().challenge(challenge).solution("solution").user(user).persist(getCurrentSession());

        Solution foundSolution = dao.findByChallengeAndUser(differentChallenge, user);

        assertThat(foundSolution, nullValue());
    }

}
