package com.ufukuzun.kodility.domain;

import javax.persistence.MappedSuperclass;
import java.util.List;

@MappedSuperclass
public abstract class PrioritisedEntity extends AbstractEntity {

    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static <T extends PrioritisedEntity> void prioritise(List<T> prioritisedEntityList) {
        int priority = 1;
        for (T each : prioritisedEntityList) {
            each.setPriority(priority);
            priority++;
        }
    }

}
