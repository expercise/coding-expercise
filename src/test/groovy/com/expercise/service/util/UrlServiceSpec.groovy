package com.expercise.service.util

import spock.lang.Specification

class UrlServiceSpec extends Specification {

    UrlService service

    def setup() {
        service = new UrlService(rootUrl: "http://www.expercise.com")
    }

    def "should create url for specified path"() {
        expect:
        "http://www.expercise.com/mypath" == service.createUrlFor("/mypath")
    }

}
