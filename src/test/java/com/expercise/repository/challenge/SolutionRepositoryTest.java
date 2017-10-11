package com.expercise.repository.challenge;

import com.expercise.BaseRepositoryTest;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.Solution;
import com.expercise.domain.level.Level;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.LevelBuilder;
import com.expercise.testutils.builder.SolutionBuilder;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static com.expercise.testutils.asserts.Asserts.assertExpectedItems;
import static com.expercise.testutils.asserts.Asserts.assertNotExpectedItems;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SolutionRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private SolutionRepository repository;

    @Test
    public void shouldFindSolutionByChallengeAndUserAndLanguage() {
        User author = new UserBuilder().persist(getEntityManager());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getEntityManager());
        User user = new UserBuilder().persist(getEntityManager());
        Solution solution = new SolutionBuilder().programmingLanguage(ProgrammingLanguage.Python).challenge(challenge).solution("solution").user(user).persist(getEntityManager());

        flushAndClear();

        Solution foundSolution = repository.findBy(challenge, user, ProgrammingLanguage.Python);

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

        Solution foundSolution = repository.findBy(differentChallenge, user, ProgrammingLanguage.JavaScript);

        assertThat(foundSolution, nullValue());
    }

    @Test
    public void shouldNotFindSolutionByDifferentLanguage() {
        User author = new UserBuilder().persist(getEntityManager());
        Challenge challenge = new ChallengeBuilder().user(author).persist(getEntityManager());
        User user = new UserBuilder().persist(getEntityManager());
        new SolutionBuilder().challenge(challenge).programmingLanguage(ProgrammingLanguage.Python).solution("solution").user(user).persist(getEntityManager());

        flushAndClear();

        Solution foundSolution = repository.findBy(challenge, user, ProgrammingLanguage.JavaScript);

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

        List<Solution> foundSolutions = repository.findSolutionsBy(challenge, user1);

        assertExpectedItems(foundSolutions, solutionPython, solutionJs);
    }

    @Test
    public void shouldFindAllSolutionsInGivenLevelsOfUser() {
        Level level1 = new LevelBuilder().persist(getEntityManager());
        Level level2 = new LevelBuilder().persist(getEntityManager());
        Level level3 = new LevelBuilder().persist(getEntityManager());

        User author = new UserBuilder().persist(getEntityManager());

        Challenge challenge1 = new ChallengeBuilder().user(author).approved(true).level(level1).persist(getEntityManager());
        Challenge challenge2 = new ChallengeBuilder().user(author).approved(true).level(level2).persist(getEntityManager());
        Challenge challenge3 = new ChallengeBuilder().user(author).approved(true).level(level2).persist(getEntityManager());
        Challenge challenge4 = new ChallengeBuilder().user(author).approved(true).level(level3).persist(getEntityManager());

        User user = new UserBuilder().persist(getEntityManager());

        Solution solution1 = new SolutionBuilder().user(user).challenge(challenge1).persist(getEntityManager());
        Solution solution2 = new SolutionBuilder().user(user).challenge(challenge2).persist(getEntityManager());
        Solution solution3 = new SolutionBuilder().user(user).challenge(challenge3).persist(getEntityManager());
        Solution solution4 = new SolutionBuilder().user(user).challenge(challenge4).persist(getEntityManager());

        flushAndClear();

        List<Solution> resultList = repository.findAllSolutionsInLevelsOf(user, Arrays.asList(level2, level3));

        assertExpectedItems(resultList, solution2, solution3, solution4);
        assertNotExpectedItems(resultList, solution1);
    }

    @Test
    public void shouldNotFindSolutionsInGivenLevelsIfChallengeIsNotApproved() {
        Level level1 = new LevelBuilder().persist(getEntityManager());
        Level level2 = new LevelBuilder().persist(getEntityManager());

        User author = new UserBuilder().persist(getEntityManager());

        Challenge challenge1 = new ChallengeBuilder().user(author).approved(true).level(level1).persist(getEntityManager());
        Challenge challenge2 = new ChallengeBuilder().user(author).approved(true).level(level2).persist(getEntityManager());
        Challenge challenge3 = new ChallengeBuilder().user(author).approved(false).level(level2).persist(getEntityManager());

        User user = new UserBuilder().persist(getEntityManager());

        Solution solution1 = new SolutionBuilder().user(user).challenge(challenge1).persist(getEntityManager());
        Solution solution2 = new SolutionBuilder().user(user).challenge(challenge2).persist(getEntityManager());
        Solution solution3 = new SolutionBuilder().user(user).challenge(challenge3).persist(getEntityManager());

        flushAndClear();

        List<Solution> resultList = repository.findAllSolutionsInLevelsOf(user, Arrays.asList(level1, level2));

        assertExpectedItems(resultList, solution1, solution2);
        assertNotExpectedItems(resultList, solution3);
    }

    @Test
    public void shouldNotFindSolutionsInGivenLevelsIfSolutionNotFromByGivenUser() {
        Level level1 = new LevelBuilder().persist(getEntityManager());
        Level level2 = new LevelBuilder().persist(getEntityManager());

        User author = new UserBuilder().persist(getEntityManager());

        Challenge challenge1 = new ChallengeBuilder().user(author).approved(true).level(level1).persist(getEntityManager());
        Challenge challenge2 = new ChallengeBuilder().user(author).approved(true).level(level2).persist(getEntityManager());
        Challenge challenge3 = new ChallengeBuilder().user(author).approved(true).level(level2).persist(getEntityManager());

        User user1 = new UserBuilder().persist(getEntityManager());
        User user2 = new UserBuilder().persist(getEntityManager());

        Solution solution1 = new SolutionBuilder().user(user1).challenge(challenge1).persist(getEntityManager());
        Solution solution2 = new SolutionBuilder().user(user1).challenge(challenge2).persist(getEntityManager());
        Solution solution3 = new SolutionBuilder().user(user2).challenge(challenge3).persist(getEntityManager());

        flushAndClear();

        List<Solution> resultList = repository.findAllSolutionsInLevelsOf(user1, Arrays.asList(level1, level2));

        assertExpectedItems(resultList, solution1, solution2);
        assertNotExpectedItems(resultList, solution3);
    }

}
