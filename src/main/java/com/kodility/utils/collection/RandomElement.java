package com.kodility.utils.collection;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Collection;

public final class RandomElement {

    private RandomElement() {
    }

    public static <T> T from(Collection<T> collection) {
        ArrayList<T> list = new ArrayList<>(collection);
        return list.isEmpty() ? null : list.get(RandomUtils.nextInt(0, list.size()));
    }

}
