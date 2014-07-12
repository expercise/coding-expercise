package com.ufukuzun.kodility.testutils.builder;

import com.ufukuzun.kodility.domain.PrioritizedEntity;

public abstract class AbstractPrioritizedEntityBuilder<T extends PrioritizedEntity, B extends AbstractPrioritizedEntityBuilder> extends AbstractEntityBuilder<T, B> {

    private int priority;

    public abstract T getInstance();

    @Override
    public T doBuild() {
        T entity = getInstance();
        entity.setPriority(priority);
        return entity;
    }

    public B priority(int priority) {
        this.priority = priority;
        return (B) this;
    }

}
