package com.kodility.controller.theme;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.level.Level;
import com.kodility.domain.theme.Theme;
import com.kodility.service.challenge.SolutionCountService;
import com.kodility.service.challenge.model.CurrentLevelModel;
import com.kodility.service.level.LevelService;
import com.kodility.service.theme.ThemeService;
import com.kodility.testutils.builder.ChallengeBuilder;
import com.kodility.testutils.builder.LevelBuilder;
import com.kodility.testutils.builder.ThemeBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kodility.testutils.asserts.Asserts.assertExpectedItems;
import static com.kodility.testutils.asserts.Asserts.assertNotExpectedItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ThemedLevelsControllerTest {

    @InjectMocks
    private ThemedLevelsController controller;

    @Mock
    private ThemeService themeService;

    @Mock
    private LevelService levelService;

    @Mock
    private SolutionCountService solutionCountService;

    @Test
    public void shouldListThemedLevels() {
        Level level1 = new LevelBuilder().priority(2).buildWithRandomId();
        Level level2 = new LevelBuilder().priority(1).buildWithRandomId();
        Level level3 = new LevelBuilder().priority(3).buildWithRandomId();

        Theme theme = new ThemeBuilder().levels(level1, level2, level3).buildWithRandomId();

        Challenge challenge1OfLevel1 = new ChallengeBuilder().approved(true).level(level1).buildWithRandomId();
        Challenge challenge2OfLevel1 = new ChallengeBuilder().approved(false).level(level1).buildWithRandomId();

        Challenge challenge1OfLevel2 = new ChallengeBuilder().approved(false).level(level2).buildWithRandomId();
        Challenge challenge2OfLevel2 = new ChallengeBuilder().approved(true).level(level2).buildWithRandomId();

        Challenge challenge1OfLevel3 = new ChallengeBuilder().approved(true).level(level3).buildWithRandomId();
        Challenge challenge2OfLevel3 = new ChallengeBuilder().approved(true).level(level3).buildWithRandomId();

        when(themeService.findById(1071L)).thenReturn(theme);

        Map<Challenge, Long> solutionCountMap = new HashMap<>();
        when(solutionCountService.prepareSolutionCountMapFor(anyListOf(Challenge.class))).thenReturn(solutionCountMap);

        CurrentLevelModel currentLevelModel = new CurrentLevelModel();
        when(levelService.getCurrentLevelModelOfCurrentUserFor(theme)).thenReturn(currentLevelModel);

        ModelAndView modelAndView = controller.listThemedLevels(1071L);

        assertThat(modelAndView.getViewName(), equalTo("theme/levelList"));
        assertThat(modelAndView.getModel(), hasEntry("theme", (Object) theme));
        assertThat(modelAndView.getModel(), hasEntry("levelList", (Object) Arrays.asList(level2, level1, level3)));
        assertThat(modelAndView.getModel(), hasEntry("solutionCountMap", (Object) solutionCountMap));
        assertThat(modelAndView.getModel(), hasEntry("currentLevelModel", (Object) currentLevelModel));

        ArgumentCaptor<List> challengeListCaptor = ArgumentCaptor.forClass(List.class);
        verify(solutionCountService).prepareSolutionCountMapFor(challengeListCaptor.capture());

        List<Challenge> approvedChallengesOfLevels = challengeListCaptor.getValue();
        assertExpectedItems(approvedChallengesOfLevels, challenge1OfLevel1, challenge2OfLevel2, challenge1OfLevel3, challenge2OfLevel3);
        assertNotExpectedItems(approvedChallengesOfLevels, challenge2OfLevel1, challenge1OfLevel2);
    }

}