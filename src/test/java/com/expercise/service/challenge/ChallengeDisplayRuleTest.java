package com.expercise.service.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.level.Level;
import com.expercise.domain.user.User;
import com.expercise.enums.UserRole;
import com.expercise.service.user.AuthenticationService;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.LevelBuilder;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChallengeDisplayRuleTest {

    @InjectMocks
    private ChallengeDisplayRule rule;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    public void shouldReturnTrueIfChallengeIsNotApprovedButUserNotAdminOrAuthorOfChallenge() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(false).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);

        assertTrue(rule.isNotDisplayable(challenge));
    }

    @Test
    public void shouldReturnFalseIfChallengeIsNotApprovedButUserIsAdmin() {
        User currentUser = new UserBuilder().userRole(UserRole.Admin).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(false).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);

        assertFalse(rule.isNotDisplayable(challenge));
    }

    @Test
    public void shouldReturnFalseIfChallengeIsNotApprovedButUserIsAuthorOfChallenge() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(false).user(currentUser).buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);

        assertFalse(rule.isNotDisplayable(challenge));
    }

    @Test
    public void shouldReturnFalseIfChallengeIsApprovedAndHasNotLevel() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(true).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);

        assertFalse(rule.isNotDisplayable(challenge));
    }

    @Test
    public void shouldReturnFalseIfChallengeIsApprovedAndHasLevelButLevelHasNotTheme() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Level level = new LevelBuilder().priority(1).buildWithRandomId();
        Challenge challenge = new ChallengeBuilder().approved(true).level(level).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);

        assertFalse(rule.isNotDisplayable(challenge));
    }

}
