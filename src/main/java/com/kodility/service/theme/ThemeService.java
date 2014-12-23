package com.kodility.service.theme;

import com.kodility.dao.theme.ThemeDao;
import com.kodility.domain.theme.Theme;
import com.kodility.enums.Lingo;
import com.kodility.service.i18n.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ThemeService {

    @Autowired
    private ThemeDao themeDao;

    @Autowired
    private MessageService messageService;

    public List<Theme> getAll() {
        List<Theme> themes = themeDao.findAllOrderedByPriority();
        themes.add(createDummyThemeForNotThemedChallenges());
        return themes;
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
