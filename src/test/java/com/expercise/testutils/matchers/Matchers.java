package com.expercise.testutils.matchers;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public final class Matchers {

    private Matchers() {
    }

    public static <T> Matcher<java.lang.Iterable<? super T>> notHasItem(T item) {
        return not(hasItem(item));
    }

}
