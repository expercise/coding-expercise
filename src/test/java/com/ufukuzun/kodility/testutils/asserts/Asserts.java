package com.ufukuzun.kodility.testutils.asserts;

import java.util.List;

import static com.ufukuzun.kodility.testutils.matchers.Matchers.notHasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public final class Asserts {

    private Asserts() {
    }

    public static <T> void assertExpectedItems(List<T> resultList, T... expectedItems) {
        assertThat(resultList, hasSize(expectedItems.length));
        assertThat(resultList, hasItems(expectedItems));
    }

    public static <T> void assertNotExpectedItems(List<T> resultList, T... notExpectedItems) {
        for (T each : notExpectedItems) {
            assertThat(resultList, notHasItem(each));
        }
    }

}
