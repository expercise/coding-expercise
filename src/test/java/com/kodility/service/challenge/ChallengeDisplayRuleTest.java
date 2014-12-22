package com.kodility.service.challenge;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.level.Level;
import com.kodility.domain.user.User;
import com.kodility.enums.UserRole;
import com.kodility.service.challenge.model.CurrentLevelModel;
import com.kodility.service.level.CurrentLevelHelper;
import com.kodility.service.user.AuthenticationService;
import com.kodility.testutils.builder.ChallengeBuilder;
import com.kodility.testutils.builder.LevelBuilder;
import com.kodility.testutils.builder.ThemeBuilder;
import com.kodility.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

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

        Level level1 = new LevelBuilder().priority(1).buildWithRandomId();
        Level level2 = new LevelBuilder().priority(2).buildWithRandomId();

        new ThemeBuilder().levels(level1, level2).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(true).level(level2).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        CurrentLevelModel currentLevelModel = new CurrentLevelModel();
        currentLevelModel.setCurrentLevel(level1);

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);
        when(currentLevelHelper.prepareCurrentLevelModelFor(currentUser, Arrays.asList(level1, level2))).thenReturn(currentLevelModel);

        assertTrue(rule.isNotDisplayable(challenge));
    }

    @Test
    public void shouldReturnFalseIfChallengeIsApprovedAndUserAtThatLevel() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Level level1 = new LevelBuilder().priority(1).buildWithRandomId();
        Level level2 = new LevelBuilder().priority(2).buildWithRandomId();

        new ThemeBuilder().levels(level1, level2).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(true).level(level1).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        CurrentLevelModel currentLevelModel = new CurrentLevelModel();
        currentLevelModel.setCurrentLevel(level1);

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);
        when(currentLevelHelper.prepareCurrentLevelModelFor(currentUser, Arrays.asList(level1, level2))).thenReturn(currentLevelModel);

        assertFalse(rule.isNotDisplayable(challenge));
    }

    @Test
    public void shouldReturnFalseIfChallengeIsApprovedAndUserAlreadyPassedThatLevel() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Level level1 = new LevelBuilder().priority(1).buildWithRandomId();
        Level level2 = new LevelBuilder().priority(2).buildWithRandomId();

        new ThemeBuilder().levels(level1, level2).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(true).level(level1).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        CurrentLevelModel currentLevelModel = new CurrentLevelModel();
        currentLevelModel.setCurrentLevel(level2);

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);
        when(currentLevelHelper.prepareCurrentLevelModelFor(currentUser, Arrays.asList(level1, level2))).thenReturn(currentLevelModel);

        assertFalse(rule.isNotDisplayable(challenge));
    }

    @Test
    public void shouldReturnFalseIfChallengeIsApprovedAndHasNotLevel() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Challenge challenge = new ChallengeBuilder().approved(true).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);

        assertFalse(rule.isNotDisplayable(challenge));

        verifyZeroInteractions(currentLevelHelper);
    }

    @Test
    public void shouldReturnFalseIfChallengeIsApprovedAndHasLevelButLevelHasNotTheme() {
        User currentUser = new UserBuilder().userRole(UserRole.User).buildWithRandomId();

        Level level = new LevelBuilder().priority(1).buildWithRandomId();
        Challenge challenge = new ChallengeBuilder().approved(true).level(level).user(new UserBuilder().buildWithRandomId()).buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);

        assertFalse(rule.isNotDisplayable(challenge));

        verifyZeroInteractions(currentLevelHelper);
    }

}
