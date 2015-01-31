package com.expercise.testutils.builder;

import com.expercise.domain.AbstractEntity;
import org.hibernate.Session;

import java.util.Random;

public abstract class AbstractEntityBuilder<T extends AbstractEntity, B extends AbstractEntityBuilder> {

    private Long id;

    protected abstract T doBuild();

    public T build() {
        T entity = doBuild();
        entity.setId(id);
        return entity;
    }

    public T buildWithRandomId() {
        T entity = build();
        entity.setId(new Random().nextLong());
        return entity;
    }

    public B id(Long id) {
        this.id = id;
        return (B) this;
    }

    public T persist(Session session) {
        T entity = build();
        session.persist(entity);
        return entity;
    }

}
