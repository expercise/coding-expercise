package com.expercise.domain;

import com.expercise.service.util.Prioritized;

import javax.persistence.MappedSuperclass;
import java.util.List;

@MappedSuperclass
public abstract class PrioritizedEntity extends AbstractEntity implements Prioritized {

    private int priority;

    public static <T extends PrioritizedEntity> void prioritize(List<T> prioritizedEntityList) {
        int priority = 1;
        for (T each : prioritizedEntityList) {
            each.setPriority(priority);
            priority++;
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}
