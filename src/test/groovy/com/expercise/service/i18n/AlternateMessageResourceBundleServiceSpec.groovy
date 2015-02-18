package com.expercise.service.i18n

import spock.lang.Specification

class AlternateMessageResourceBundleServiceSpec extends Specification {

    AlternateMessageResourceBundleService service = new AlternateMessageResourceBundleService()

    def "should get message from resource"() {
        expect:
        "bundleValue1" == service.getMessage("testBundle", "bundleKey1")
        "bundleValue2" == service.getMessage("testBundle", "bundleKey2")
    }

    def "should get not found key with some chars when key does not found"() {
        expect:
        "???_differentKey_???" == service.getMessage("testBundle", "differentKey")
    }

}
