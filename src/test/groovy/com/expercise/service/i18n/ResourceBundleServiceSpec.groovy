package com.expercise.service.i18n

import spock.lang.Specification

class ResourceBundleServiceSpec extends Specification {

    ResourceBundleService service = new ResourceBundleService()

    def "should get message from resource"() {
        expect:
        "bundleValue2" == service.getMessage("bundles.testbundle", "bundlekey2")
    }

    def "should get not found key with some chars when key does not found"() {
        expect:
        "???_differentKey_???" == service.getMessage("bundles.testbundle", "differentKey")
    }

}
