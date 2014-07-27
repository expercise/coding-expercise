package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.UserRole;
import com.ufukuzun.kodility.service.challenge.model.CurrentLevelModel;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import com.ufukuzun.kodility.testutils.builder.ChallengeBuilder;
import com.ufukuzun.kodility.testutils.builder.LevelBuilder;
import com.ufukuzun.kodility.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChallengeDisplayRuleTest {

    @InjectMocks
    private ChallengeDisplayRule rule;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private CurrentLevelHelper currentLevelHelper;

    @Test
    public void shouldReturnTrueIfChallengeIsNull() {
        assertTrue(rule.isNotDisplayable(null));

        verifyZeroInteractions(authenticationService, currentLevelHelper);
    }

    @Test
    public void shouldReturnTrueIfChallengeIsNotApprovedButUserNotAdminOrAuthorOfChallenge() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(false).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);

        assertTrue(rule.isNotDisplayable(challenge));

        verifyZeroInteractions(currentLevelHelper);
    }

    @Test
    public void shouldReturnFalseIfChallengeIsNotApprovedButUserIsAdmin() {
        User currentUser = new UserBuilder().userRole(UserRole.Admin).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(false).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);

        assertFalse(rule.isNotDisplayable(challenge));

        verifyZeroInteractions(currentLevelHelper);
    }

    @Test
    public void shouldReturnFalseIfChallengeIsNotApprovedButUserIsAuthorOfChallenge() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(false).user(currentUser).buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);

        assertFalse(rule.isNotDisplayable(challenge));

        verifyZeroInteractions(currentLevelHelper);
    }

    @Test
    public void shouldReturnTrueIfChallengeIsApprovedButUserNotReachedThatLevelYet() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(true).level(new LevelBuilder().priority(2).buildWithRandomId()).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        CurrentLevelModel currentLevelModel = new CurrentLevelModel();
        currentLevelModel.setCurrentLevel(new LevelBuilder().priority(1).buildWithRandomId());

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);
        when(currentLevelHelper.prepareCurrentLevelModelFor(currentUser)).thenReturn(currentLevelModel);

        assertTrue(rule.isNotDisplayable(challenge));
    }

    @Test
    public void shouldReturnFalseIfChallengeIsApprovedAndUserAtThatLevel() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(true).level(new LevelBuilder().priority(1).buildWithRandomId()).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        CurrentLevelModel currentLevelModel = new CurrentLevelModel();
        currentLevelModel.setCurrentLevel(new LevelBuilder().priority(1).buildWithRandomId());

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);
        when(currentLevelHelper.prepareCurrentLevelModelFor(currentUser)).thenReturn(currentLevelModel);

        assertFalse(rule.isNotDisplayable(challenge));
    }

    @Test
    public void shouldReturnFalseIfChallengeIsApprovedAndUserAlreadyPassedThatLevel() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(true).level(new LevelBuilder().priority(1).buildWithRandomId()).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        CurrentLevelModel currentLevelModel = new CurrentLevelModel();
        currentLevelModel.setCurrentLevel(new LevelBuilder().priority(2).buildWithRandomId());

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);
        when(currentLevelHelper.prepareCurrentLevelModelFor(currentUser)).thenReturn(currentLevelModel);

        assertFalse(rule.isNotDisplayable(challenge));
    }

    @Test
    public void shouldReturnFalseIfChallengeIsApprovedAndHasNotLevel() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(true).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        CurrentLevelModel currentLevelModel = new CurrentLevelModel();
        currentLevelModel.setCurrentLevel(new LevelBuilder().priority(2).buildWithRandomId());

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);
        when(currentLevelHelper.prepareCurrentLevelModelFor(currentUser)).thenReturn(currentLevelModel);

        assertFalse(rule.isNotDisplayable(challenge));
    }

}
