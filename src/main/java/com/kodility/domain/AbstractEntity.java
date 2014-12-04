package com.kodility.domain;

import org.hibernate.Hibernate;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {

    public abstract Long getId();

    public abstract void setId(Long id);

    public boolean isPersisted() {
        return getId() != null;
    }

    @Override
    public boolean equals(Object toCompare) {
        if (this == toCompare) {
            return true;
        }

        if (toCompare == null) {
            return false;
        }

        if (Hibernate.getClass(this) != Hibernate.getClass(toCompare)) {
            return false;
        }

        AbstractEntity otherEntity = (AbstractEntity) toCompare;
        if (getId() == null) {
            if (otherEntity.getId() != null) {
                return false;
            }
        } else if (!getId().equals(otherEntity.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int magicNumber = 13;
        return magicNumber + ((getId() == null) ? 0 : getId().hashCode());
    }

}
