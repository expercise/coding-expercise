package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.AbstractDaoTest;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
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
    public void shouldFindSolutionByChallengeAndUserAndLanguage() {
        User author = new UserBuilder().persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());
        User user = new UserBuilder().persist(getCurrentSession());
        Solution solution = new SolutionBuilder().programmingLanguage(ProgrammingLanguage.Python).challenge(challenge).solution("solution").user(user).persist(getCurrentSession());

        Solution foundSolution = dao.findBy(challenge, user, ProgrammingLanguage.Python);

        assertThat(foundSolution, equalTo(solution));
    }

    @Test
    public void shouldNotFindSolutionByDifferentChallengeAndUser() {
        User author = new UserBuilder().persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());
        Challenge differentChallenge = new ChallengeBuilder().user(author).persist(getCurrentSession());
        User user = new UserBuilder().persist(getCurrentSession());
        new SolutionBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.Python).solution("solution").user(user).persist(getCurrentSession());

        Solution foundSolution = dao.findBy(differentChallenge, user, ProgrammingLanguage.JavaScript);

        assertThat(foundSolution, nullValue());
    }

    @Test
    public void shouldNotFindSolutionByDifferentLanguage() {
        User author = new UserBuilder().persist(getCurrentSession());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getCurrentSession());
        User user = new UserBuilder().persist(getCurrentSession());
        new SolutionBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.Python).solution("solution").user(user).persist(getCurrentSession());

        Solution foundSolution = dao.findBy(challenge, user, ProgrammingLanguage.JavaScript);

        assertThat(foundSolution, nullValue());
    }

}
