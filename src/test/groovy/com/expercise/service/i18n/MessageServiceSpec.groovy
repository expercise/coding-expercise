package com.expercise.service.i18n

import spock.lang.Specification

class MessageServiceSpec extends Specification {

    MessageService service

    AlternateMessageResourceBundleService alternateMessageResourceBundleService = Mock()

    def setup() {
        service = new MessageService(alternateMessageResourceBundleService: alternateMessageResourceBundleService)
    }

    def "should get message for email from resource bundle"() {
        given:
        1 * alternateMessageResourceBundleService.getMessage("messagesForEmails", "emailKey") >> "emailValue"

        expect:
        "emailValue" == service.getMessageForEmail("emailKey")
    }

}
