package com.expercise.service.theme;

import com.expercise.repository.challenge.ChallengeDao;
import com.expercise.repository.theme.ThemeDao;
import com.expercise.domain.theme.Theme;
import com.expercise.enums.Lingo;
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
    private ThemeDao themeDao;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChallengeDao challengeDao;

    public List<Theme> getAll() {
        List<Theme> themes = themeDao.findAllOrderedByPriority();
        themes.add(createDummyThemeForNotThemedChallenges());
        return themes;
    }

    public Map<Theme, Long> prepareChallengeCounts(List<Theme> themes) {
        return themes.stream()
                .collect(Collectors.toMap(
                        t -> t,
                        t -> {
                            if (t.isPersisted()) {
                                return challengeDao.countApprovedChallengesIn(t);
                            } else {
                                return challengeDao.countNotThemedApprovedChallenges();
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
        return themeDao.findOne(id);
    }

}
