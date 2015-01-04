package com.kodility.service.util

import spock.lang.Specification

class UrlServiceSpec extends Specification {

    UrlService service

    def setup() {
        service = new UrlService(rootUrl: "http://www.kodility.com")
    }

    def "should create url for specified path"() {
        expect:
        "http://www.kodility.com/mypath" == service.createUrlFor("/mypath")
    }

}
