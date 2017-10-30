package com.expercise.service.util

import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

class UrlServiceSpec extends Specification {

    UrlService service

    MockHttpServletRequest mockHttpServletRequest

    def setup() {
        service = new UrlService(rootUrl: "http://www.expercise.com")
        mockHttpServletRequest = new MockHttpServletRequest()
    }

    def "should create url for specified path"() {
        expect:
        "http://expercise.com/mypath" == service.createUrlFor("/mypath")
    }

    def "should create canonical url with request uri and root url, without www"() {
        given:
        mockHttpServletRequest.setRequestURI("/tags")

        expect:
        "http://expercise.com/tags" == service.getCanonical(mockHttpServletRequest)
    }

}
