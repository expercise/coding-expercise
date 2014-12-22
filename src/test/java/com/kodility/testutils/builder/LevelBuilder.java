package com.kodility.testutils.builder;

import com.kodility.domain.level.Level;
import com.kodility.domain.theme.Theme;

public class LevelBuilder extends AbstractPrioritizedEntityBuilder<Level, LevelBuilder> {

    private Theme theme;

    @Override
    protected Level getInstance() {
        Level level = new Level();
        level.setTheme(theme);
        return level;
    }

    public LevelBuilder theme(Theme theme) {
        this.theme = theme;
        return this;
    }

}
