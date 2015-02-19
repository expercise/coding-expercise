package com.expercise.testutils.builder;

import com.expercise.domain.level.Level;
import com.expercise.domain.theme.Theme;
import com.expercise.enums.Lingo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThemeBuilder extends AbstractPrioritizedEntityBuilder<Theme, ThemeBuilder> {

    private Map<Lingo, String> names = new HashMap<>();

    private List<Level> levels = new ArrayList<>();

    @Override
    protected Theme getInstance() {
        Theme theme = new Theme();
        theme.setNames(names);
        theme.setLevels(levels);
        levels.forEach(l -> l.setTheme(theme));
        return theme;
    }

    public ThemeBuilder levels(Level... levels) {
        this.levels = Stream.of(levels).collect(Collectors.toList());
        return this;
    }

    public ThemeBuilder addName(Lingo lingo, String name) {
        this.names.put(lingo, name);
        return this;
    }

}
