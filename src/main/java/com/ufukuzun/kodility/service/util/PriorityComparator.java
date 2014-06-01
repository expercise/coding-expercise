package com.ufukuzun.kodility.service.util;

import java.util.Comparator;

public final class PriorityComparator implements Comparator<Prioritized> {

    private static PriorityComparator instance = null;

    private PriorityComparator() {}

    @Override
    public int compare(Prioritized o1, Prioritized o2) {
        return Integer.compare(o1.getPriority(), o2.getPriority());
    }

    public static PriorityComparator getInstance() {
        if (instance == null) {
            instance = new PriorityComparator();
        }
        return instance;
    }

}
