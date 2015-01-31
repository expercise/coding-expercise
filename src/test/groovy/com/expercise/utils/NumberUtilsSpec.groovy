package com.expercise.utils

import spock.lang.Specification
import spock.lang.Unroll

class NumberUtilsSpec extends Specification {

    @Unroll
    def "should calculate percentage as #percentage when partial is #partial and total is #total"(int partial, int total, int percentage) {
        expect:
        NumberUtils.toPercentage(partial, total) == percentage

        where:
        partial | total | percentage
        1       | 2     | 50
        49      | 100   | 49
        3       | 4     | 75
        11      | 12    | 92
    }

}
