package com.expercise.service.i18n

import spock.lang.Specification

class MessageServiceSpec extends Specification {

    MessageService service

    ResourceBundleService resourceBundleService = Mock()

    def setup() {
        service = new MessageService(resourceBundleService: resourceBundleService)
    }

    def "should get email message from resource bundle"() {
        given:
        1 * resourceBundleService.getMessage("emails", "emailKey") >> "emailValue"

        expect:
        "emailValue" == service.getEmailMessage("emailKey")
    }

}
