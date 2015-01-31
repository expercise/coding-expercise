package com.expercise.testutils.builder;

import com.expercise.domain.level.Level;
import com.expercise.domain.theme.Theme;

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
