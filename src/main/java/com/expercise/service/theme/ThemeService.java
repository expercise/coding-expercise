package com.expercise.service.theme;

import com.expercise.domain.theme.Theme;
import com.expercise.enums.Lingo;
import com.expercise.repository.challenge.ChallengeRepository;
import com.expercise.repository.theme.ThemeRepository;
import com.expercise.service.i18n.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChallengeRepository challengeRepository;

    public List<Theme> getAll() {
        List<Theme> themes = themeRepository.findAllByOrderByPriority();
        themes.add(createDummyThemeForNotThemedChallenges());
        return themes;
    }

    public Map<Theme, Long> prepareChallengeCounts(List<Theme> themes) {
        return themes.stream()
                .collect(Collectors.toMap(
                        t -> t,
                        t -> {
                            if (t.isPersisted()) {
                                return challengeRepository.countByApprovedIsTrueAndLevelTheme(t);
                            } else {
                                return challengeRepository.countNotThemedApprovedChallenges();
                            }
                        })
                );
    }

    private Theme createDummyThemeForNotThemedChallenges() {
        Theme theme = new Theme();
        Stream.of(Lingo.values())
                .forEach(l -> theme.getNames().put(l, messageService.getMessage("header.notThemedChallenges", l.getLocale())));
        return theme;
    }

    public Theme findById(Long id) {
        return themeRepository.findOne(id);
    }

}
