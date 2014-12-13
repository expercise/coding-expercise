package com.kodility.testutils.builder;

import com.kodility.domain.level.Level;

public class LevelBuilder extends AbstractPrioritizedEntityBuilder<Level, LevelBuilder> {

    @Override
    protected Level getInstance() {
        return new Level();
    }

}
