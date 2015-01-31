package com.expercise.utils.collection;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class RandomElement {

    private RandomElement() {
    }

    public static <T> T from(Collection<T> collection) {
        List<T> list = new ArrayList<>(collection);
        return list.isEmpty() ? null : list.get(RandomUtils.nextInt(0, list.size()));
    }

    public static <T> T from(T[] array) {
        return from(Arrays.asList(array));
    }

}
