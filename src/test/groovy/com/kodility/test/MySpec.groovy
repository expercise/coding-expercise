package com.kodility.test

import spock.lang.Specification


class MySpec extends Specification {

    def "test kodility specs through spock"() {
        expect: "spock".size() == 5
    }

}