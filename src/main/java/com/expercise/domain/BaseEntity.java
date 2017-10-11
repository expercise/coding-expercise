package com.expercise.domain;

import org.hibernate.Hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR", strategy = GenerationType.SEQUENCE)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPersisted() {
        return getId() != null;
    }

    public boolean isNotPersisted() {
        return !isPersisted();
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

        BaseEntity otherEntity = (BaseEntity) toCompare;
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
