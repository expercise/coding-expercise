package com.expercise.testutils.asserts;

import java.util.List;

import static com.expercise.testutils.matchers.Matchers.notHasItem;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public final class Asserts {

    private Asserts() {
    }

    @SafeVarargs
    public static <T> void assertExpectedItems(List<T> resultList, T... expectedItems) {
        assertThat(resultList, hasSize(expectedItems.length));
        assertThat(resultList, hasItems(expectedItems));
    }

    @SafeVarargs
    public static <T> void assertNotExpectedItems(List<T> resultList, T... notExpectedItems) {
        for (T each : notExpectedItems) {
            assertThat(resultList, notHasItem(each));
        }
    }

    @SafeVarargs
    public static <T> void assertOrderedItems(List<T> resultList, T... orderedItems) {
        assertThat(resultList, hasSize(orderedItems.length));
        for (int index = 0; index < orderedItems.length; index++) {
            assertThat(resultList.get(index), equalTo(orderedItems[index]));
        }
    }

}
