package com.ufukuzun.kodility.testutils.builder;

import com.ufukuzun.kodility.domain.challenge.Level;

public class LevelBuilder extends AbstractPrioritizedEntityBuilder<Level, LevelBuilder> {

    @Override
    protected Level getInstance() {
        return new Level();
    }

}
