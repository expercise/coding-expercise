package com.expercise.testutils.builder;

import com.expercise.domain.BaseEntity;

import javax.persistence.EntityManager;
import java.util.Random;

public abstract class BaseEntityBuilder<T extends BaseEntity, B extends BaseEntityBuilder> {

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

    public T persist(EntityManager entityManager) {
        T entity = build();
        entityManager.persist(entity);
        return entity;
    }

}
