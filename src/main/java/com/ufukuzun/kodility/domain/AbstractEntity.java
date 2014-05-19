package com.ufukuzun.kodility.domain;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {

    public abstract Long getId();

    public abstract void setId(Long id);

    public boolean isPersisted() {
        return getId() != null;
    }

}
