package com.expercise.service.util;

import java.util.Comparator;

public class PrioritySorter implements Comparator<Prioritized> {

    @Override
    public int compare(Prioritized o1, Prioritized o2) {
        return Integer.compare(o1.getPriority(), o2.getPriority());
    }

}
