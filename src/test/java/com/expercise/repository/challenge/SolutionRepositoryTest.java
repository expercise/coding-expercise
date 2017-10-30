package com.expercise.repository.challenge;

import com.expercise.BaseSpringIntegrationTest;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.Solution;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.SolutionBuilder;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.expercise.testutils.asserts.Asserts.assertExpectedItems;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SolutionRepositoryTest extends BaseSpringIntegrationTest {

    @Autowired
    private SolutionRepository repository;

    @Test
    public void shouldFindSolutionByChallengeAndUserAndLanguage() {
        User author = new UserBuilder().persist(getEntityManager());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getEntityManager());
        User user = new UserBuilder().persist(getEntityManager());
        Solution solution = new SolutionBuilder().programmingLanguage(ProgrammingLanguage.Python).challenge(challenge).solution("solution").user(user).persist(getEntityManager());

        flushAndClear();

        Solution foundSolution = repository.findByChallengeAndUserAndProgrammingLanguage(challenge, user, ProgrammingLanguage.Python);

        assertThat(foundSolution, equalTo(solution));
    }

    @Test
    public void shouldNotFindSolutionByDifferentChallengeAndUser() {
        User author = new UserBuilder().persist(getEntityManager());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getEntityManager());
        Challenge differentChallenge = new ChallengeBuilder().user(author).persist(getEntityManager());
        User user = new UserBuilder().persist(getEntityManager());
        new SolutionBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.Python).solution("solution").user(user).persist(getEntityManager());

        flushAndClear();

        Solution foundSolution = repository.findByChallengeAndUserAndProgrammingLanguage(differentChallenge, user, ProgrammingLanguage.JavaScript);

        assertThat(foundSolution, nullValue());
    }

    @Test
    public void shouldNotFindSolutionByDifferentLanguage() {
        User author = new UserBuilder().persist(getEntityManager());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getEntityManager());
        User user = new UserBuilder().persist(getEntityManager());
        new SolutionBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.Python).solution("solution").user(user).persist(getEntityManager());

        flushAndClear();

        Solution foundSolution = repository.findByChallengeAndUserAndProgrammingLanguage(challenge, user, ProgrammingLanguage.JavaScript);

        assertThat(foundSolution, nullValue());
    }

    @Test
    public void shouldFindSolutionsByChallengeAndUser() {
        User author = new UserBuilder().persist(getEntityManager());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getEntityManager());
        User user1 = new UserBuilder().persist(getEntityManager());
        User user2 = new UserBuilder().persist(getEntityManager());
        Solution solutionPython = new SolutionBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.Python).solution("solution for py").user(user1).persist(getEntityManager());
        Solution solutionJs = new SolutionBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.JavaScript).solution("solution for js").user(user1).persist(getEntityManager());
        new SolutionBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.JavaScript).solution("solution for js - 2").user(user2).persist(getEntityManager());

        flushAndClear();

        List<Solution> foundSolutions = repository.findByChallengeAndUser(challenge, user1);

        assertExpectedItems(foundSolutions, solutionPython, solutionJs);
    }

}
