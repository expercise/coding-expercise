package com.expercise.controller.theme;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.level.Level;
import com.expercise.domain.theme.Theme;
import com.expercise.enums.Lingo;
import com.expercise.service.challenge.SolutionCountService;
import com.expercise.service.challenge.model.CurrentLevelModel;
import com.expercise.service.level.LevelService;
import com.expercise.service.theme.ThemeService;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.LevelBuilder;
import com.expercise.testutils.builder.ThemeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static com.expercise.testutils.asserts.Asserts.assertExpectedItems;
import static com.expercise.testutils.asserts.Asserts.assertNotExpectedItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocaleContextHolder.class})
public class ThemedLevelsControllerTest {

    @InjectMocks
    private ThemedLevelsController controller;

    @Mock
    private ThemeService themeService;

    @Mock
    private LevelService levelService;

    @Mock
    private SolutionCountService solutionCountService;

    @Before
    public void before() {
        mockStatic(LocaleContextHolder.class);
        when(LocaleContextHolder.getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    public void shouldListThemedLevels() {
        Level level1 = new LevelBuilder().priority(2).buildWithRandomId();
        Level level2 = new LevelBuilder().priority(1).buildWithRandomId();
        Level level3 = new LevelBuilder().priority(3).buildWithRandomId();

        Theme theme = new ThemeBuilder().id(1071L).addName(Lingo.English, "Theme Name").levels(level1, level2, level3).build();

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

        ModelAndView modelAndView = controller.listThemedLevels(1071L, "theme-name");

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

    @Test
    public void shouldRedirect404IfThemeNotFound() {
        when(themeService.findById(1453L)).thenReturn(null);

        ModelAndView modelAndView = controller.listThemedLevels(1453L, "bookmarkableThemeName");

        assertThat(modelAndView.getViewName(), equalTo("redirect:/404"));

        verifyZeroInteractions(levelService, solutionCountService);
    }

    @Test
    public void shouldRedirectNewUrlIfThemeBookmarkableUrlNotMatch() {
        Theme theme = new ThemeBuilder().id(1071L).addName(Lingo.English, "New Theme Name").build();

        when(themeService.findById(1071L)).thenReturn(theme);

        ModelAndView modelAndView = controller.listThemedLevels(1071L, "old-theme-name");

        assertThat(modelAndView.getViewName(), equalTo("redirect:/themes/1071/new-theme-name"));

        verifyZeroInteractions(levelService, solutionCountService);
    }

}