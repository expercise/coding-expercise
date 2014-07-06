package com.ufukuzun.kodility.service.util;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class PrioritySorter implements Comparator<Prioritized> {

    @Override
    public int compare(Prioritized o1, Prioritized o2) {
        return Integer.compare(o1.getPriority(), o2.getPriority());
    }

    public void sort(List<? extends Prioritized> list) {
        Collections.sort(list, this);
    }

}
